package com.example.mod_home.ui.activity

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mod_home.adapters.VideoAdapter
import com.example.mod_home.databinding.ActivityLookRecordBinding
import com.example.mod_home.viewmodel.LookRecordViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.bean.VideoBean
import com.xwl.common_lib.utils.ClickUtil

class LookRecordActivity : BaseVmVbActivity<LookRecordViewModel, ActivityLookRecordBinding>() {
    private lateinit var mAdapter: VideoAdapter
    private var mCurrentPage = 1
    private var size = 10
    private var mIsRefresh = true
    override fun initView(savedInstanceState: Bundle?) {
        initRv()
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
        mViewBinding.rv.adapter = mAdapter
        mAdapter.isEmptyViewEnable = true
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
                        VideoDetailActivity.startActivity(
                            this@LookRecordActivity,
                            it
                        )
                    }
            }
        })
    }

    private fun getData(page: Int, size: Int, showloading: Boolean = true) {
        mViewModel.getRecommendList(page, size, showloading).observe(this) {
            if (it != null) {
                it.let {
                    if (mIsRefresh) {
                        if (it.isEmpty()) {
                            mAdapter.setEmptyViewLayout(
                                this@LookRecordActivity,
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
                        this@LookRecordActivity,
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
}