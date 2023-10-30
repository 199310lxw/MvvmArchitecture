package com.example.mod_home.ui.activity

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mod_home.adapters.CollectionAdapter
import com.example.mod_home.databinding.ActivityCollectedBinding
import com.example.mod_home.viewmodel.CollectedViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.dialog.MessageDialog
import com.xwl.common_lib.bean.CollectionBean
import com.xwl.common_lib.bean.User
import com.xwl.common_lib.bean.VideoBean
import com.xwl.common_lib.constants.CollectionType
import com.xwl.common_lib.provider.UserServiceProvider
import com.xwl.common_lib.utils.ClickUtil

class CollectedActivity : BaseVmVbActivity<CollectedViewModel, ActivityCollectedBinding>() {
    private lateinit var mAdapter: CollectionAdapter
    private var user: User? = null
    override fun initView(savedInstanceState: Bundle?) {
        initRv()
        user = UserServiceProvider.getUserInfo()
        mViewBinding.refreshLayout.setEnableLoadMore(false)
        mViewBinding.refreshLayout.setOnRefreshListener {
            user?.let { it1 -> getData(it1.phone, false) }
        }
    }

    override fun initData() {
        user?.let { it1 -> getData(it1.phone) }
    }

    private fun initRv() {
        mAdapter = CollectionAdapter()
        mViewBinding.rv.adapter = mAdapter
        mAdapter.isEmptyViewEnable = true
        mAdapter.setOnItemClickListener(object :
            BaseQuickAdapter.OnItemClickListener<CollectionBean> {
            override fun onClick(
                adapter: BaseQuickAdapter<CollectionBean, *>,
                view: View,
                position: Int
            ) {
                if (ClickUtil.isFastClick()) {
                    return
                }
                val video = VideoBean()
                video.title = adapter.getItem(position)?.title
                video.videoUrl = adapter.getItem(position)?.url
                video.type = adapter.getItem(position)?.videoType
                video.posterUrl = adapter.getItem(position)?.posterUrl
                adapter.getItem(position)?.let {
                    VideoDetailActivity.startActivity(
                        this@CollectedActivity,
                        video
                    )
                }
            }
        })

        mAdapter.setOnItemLongClickListener { _, _, position ->
            mAdapter.getItem(position)?.let { showDeleteDialog(it.url) }
            false
        }

    }

    private fun showDeleteDialog(url: String) {
        MessageDialog.Builder(this@CollectedActivity)
            .setCancelText("取消")
            .setConfirmText("删除")
            .setContent("确定删除该收藏内容？")
            .setOnCancelClickListener { }
            .setOnConfirmClickListener {
                user?.let {
                    mViewModel.disCollectVideo(
                        it.phone,
                        url,
                        true
                    ).observe(this@CollectedActivity) { it1 ->
                        if (it1 == "true") {
                            getData(it.phone)
                        }
                    }
                }
            }
    }

    private fun getData(phone: String, showloading: Boolean = true) {
        mViewModel.getCollectedList(phone, CollectionType.VIDEO.name, showloading)
            .observe(this) {
                if (it != null) {
                    it.let {
                        if (it.isEmpty()) {
                            mAdapter.setEmptyViewLayout(
                                this@CollectedActivity,
                                com.xwl.common_lib.R.layout.view_empty_data
                            )
                        } else {
                            mAdapter.submitList(it)
                        }

                        mViewBinding.refreshLayout.finishRefresh()
                    }
                } else {
                    mAdapter.setEmptyViewLayout(
                        this@CollectedActivity,
                        com.xwl.common_lib.R.layout.view_empty_data
                    )
                    mViewBinding.refreshLayout.finishRefresh()
                }
            }
    }
}