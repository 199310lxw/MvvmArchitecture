package com.example.mod_basics

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mod_basics.adapter.VideoAdapter
import com.example.mod_basics.databinding.ActivityMainBinding
import com.example.mod_basics.viewmodel.MainViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.dialog.AgreeMentDialog
import com.xwl.common_lib.bean.VideoBean
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.manager.MMKVAction
import com.xwl.common_lib.utils.ClickUtil

@Route(path = RoutMap.BASIC_ACTIVITY_MAIN)
class MainActivity : BaseVmVbActivity<MainViewModel, ActivityMainBinding>() {
    private lateinit var mAdapter: VideoAdapter

    private var mCurrentPage = 1
    private var size = 10
    private var mIsRefresh = true
    override fun initView(savedInstanceState: Bundle?) {
        initRv()
        mViewBinding.btnToAllFunction.onClick {
            showAgreeMentDialog()
        }


        mViewBinding.refreshLayout.setEnableLoadMore(false)
        mViewBinding.refreshLayout.setOnRefreshListener {
            mIsRefresh = true
            mCurrentPage = 1
            getData(mCurrentPage, size, false)
        }
        mViewBinding.refreshLayout.setOnLoadMoreListener {
            mIsRefresh = false
            mCurrentPage++
            getData(mCurrentPage, size, false)
        }
    }

    override fun initData() {
        getData(mCurrentPage, size)
    }

    private fun initRv() {
        mAdapter = VideoAdapter()
        mViewBinding.rvVideo.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : BaseQuickAdapter.OnItemClickListener<VideoBean> {
            override fun onClick(
                adapter: BaseQuickAdapter<VideoBean, *>,
                view: View,
                position: Int
            ) {
                if (ClickUtil.isFastClick()) {
                    return
                }
                adapter.getItem(position)
                    ?.let {
                        BasicsVideoDetailActivity.startActivity(
                            this@MainActivity,
                            it
                        )
                    }
            }
        })
    }

    private fun getData(page: Int, size: Int, showloading: Boolean = true) {
        mViewModel.getVideoList(page, size, showloading).observe(this) {
            if (it != null) {
                it.let {
                    if (mIsRefresh) {
                        if (it.isEmpty()) {
                            mAdapter.setEmptyViewLayout(
                                this@MainActivity,
                                com.xwl.common_lib.R.layout.view_empty_data
                            )
                        } else if (it.size >= size) {
                            mViewBinding.refreshLayout.setEnableLoadMore(true)
                        }
                        mAdapter.submitList(it)
                        mViewBinding.refreshLayout.finishRefresh()
                    } else {
                        if (it.isEmpty()) {
                            mCurrentPage--
                            mViewBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                            return@let
                        }
                        mAdapter.addAll(it)
                        mViewBinding.refreshLayout.finishLoadMore()
                    }
                }
            } else {
                if (mIsRefresh) {
                    mAdapter.setEmptyViewLayout(
                        this@MainActivity,
                        com.xwl.common_lib.R.layout.view_empty_data
                    )
                    mViewBinding.refreshLayout.finishRefresh()
                } else {
                    mCurrentPage--
                    mViewBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                }
            }
        }
    }

    fun showAgreeMentDialog() {
        AgreeMentDialog.Builder(this@MainActivity)
            .setAgreeClickListener {
                MMKVAction.getDefaultMKKV().encode(KeyConstant.KEY_AGREEMENT_STATUS, true)
                toHomePage()
            }
            .setDisAgreeClickListener {
                MMKVAction.getDefaultMKKV().encode(KeyConstant.KEY_AGREEMENT_STATUS, false)
            }
            .show()
    }

    fun toHomePage() {
        ARouter.getInstance().build(RoutMap.HOME_ACTIVITY_HOME)
            .navigation(this@MainActivity, object : NavCallback() {
                override fun onArrival(postcard: Postcard?) {
                    finish()
                }
            })
    }
}