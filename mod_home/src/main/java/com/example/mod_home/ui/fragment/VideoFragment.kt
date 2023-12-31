package com.example.mod_home.ui.fragment

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mod_home.adapters.VideoAdapter
import com.example.mod_home.databinding.FragmentVideoBinding
import com.example.mod_home.ui.activity.VideoDetailActivity
import com.example.mod_home.viewmodel.HomeViewModel
import com.orhanobut.logger.Logger
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.bean.VideoBean
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.provider.LoginServiceProvider
import com.xwl.common_lib.provider.UserServiceProvider
import com.xwl.common_lib.utils.ClickUtil

class VideoFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentVideoBinding>() {
    private lateinit var mAdapter: VideoAdapter

    private var mCurrentPage = 1
    private var size = 10
    private var mIsRefresh = true

    companion object {
        fun newInstance() = VideoFragment()
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
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
            Logger.i("----->${mCurrentPage}")
            getData(mCurrentPage, size, false)
        }
    }

    override fun onLazyLoadData() {
        getData(1, size)
        mViewModel.error.observe(this) {
            TipsToast.showTips(it)
            mViewBinding.refreshLayout.finishRefresh()
        }
    }

    private fun getData(page: Int, size: Int, showloading: Boolean = true) {
        mViewModel.getVideoList(page, size, showloading).observe(this) {
            if (it != null) {
                it.let {
                    if (mIsRefresh) {
                        if (it.isEmpty()) {
                            activity?.let { it1 ->
                                mAdapter.setEmptyView()
                            }
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
                    mAdapter.setEmptyView()
                    mViewBinding.refreshLayout.finishRefresh()
                } else {
                    mCurrentPage--
                    mViewBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                }
            }
        }
    }

    private fun initRv() {
        mAdapter = VideoAdapter()
        mViewBinding.rv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseQuickAdapter.OnItemClickListener<VideoBean> {
            override fun onClick(
                adapter: BaseQuickAdapter<VideoBean, *>,
                view: View,
                position: Int
            ) {
                if (ClickUtil.isFastClick()) {
                    return
                }
                if (UserServiceProvider.isLogin()) {
                    adapter.getItem(position)
                        ?.let {
                            VideoDetailActivity.startActivity(
                                mContext,
                                it
                            )
                        }
                } else {
                    LoginServiceProvider.skipLoginActivity(mContext)
                }
            }
        })
    }

}