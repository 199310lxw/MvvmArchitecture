package com.example.mod_home.ui.fragment

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mod_home.adapters.VideoAdapter
import com.example.mod_home.databinding.FragmentHotBinding
import com.example.mod_home.ui.activity.VideoDetailActivity
import com.example.mod_home.viewmodel.HomeViewModel
import com.orhanobut.logger.Logger
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.bean.HotBean
import com.xwl.common_lib.provider.LoginServiceProvider
import com.xwl.common_lib.provider.UserServiceProvider
import com.xwl.common_lib.utils.ClickUtil

class HotFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentHotBinding>() {
    private lateinit var mAdapter: VideoAdapter

    private var mCurrentPage = 1
    private var size = 10
    private var mIsRefresh = true

    companion object {
        fun newInstance() = HotFragment()
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
            Logger.e("----->${mCurrentPage}")
            getData(mCurrentPage, size, false)
        }
    }

    override fun onLazyLoadData() {
        getData(1, size)
    }

    private fun getData(page: Int, size: Int, showloading: Boolean = true) {
        mViewModel.getHotList(page, size, showloading).observe(this) {
            if (it != null) {
                it.let {
                    if (mIsRefresh) {
                        if (it.isEmpty()) {
                            activity?.let { it1 ->
                                mAdapter.setEmptyViewLayout(
                                    it1,
                                    com.xwl.common_lib.R.layout.view_empty_data
                                )
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
                    mAdapter.setEmptyViewLayout(
                        mContext,
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

    private fun initRv() {
        mAdapter = VideoAdapter()
        mAdapter.isEmptyViewEnable = true
        mAdapter.setItemAnimation(BaseQuickAdapter.AnimationType.ScaleIn)
//        val helper = QuickAdapterHelper.Builder(mAdapter)
        //设置尾部加载更多
//            .setConfig(ConcatAdapter.Config.DEFAULT)
//            .build()
        mViewBinding.rv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseQuickAdapter.OnItemClickListener<HotBean> {
            override fun onClick(adapter: BaseQuickAdapter<HotBean, *>, view: View, position: Int) {
                if (ClickUtil.isFastClick()) {
                    return
                }
                if (UserServiceProvider.isLogin()) {

                    adapter.getItem(position)
                        ?.let {
                            VideoDetailActivity.startActivity(
                                mContext,
                                it.videoUrl,
                                it.posterUrl,
                                it.title
                            )
                        }
//                    adapter.getItem(position)
//                        ?.let { CourseListActivity.startActivity(mContext, it.type) }
                } else {
                    LoginServiceProvider.skipLoginActivity(mContext)
//                    activity?.let { LoginDialogUtil.show(it, "用户未登陆，是否跳转登录页登录") }
                }
            }
        })
    }

}