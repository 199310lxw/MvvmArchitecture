package com.example.mod_home.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.mod_home.adapters.HotAdapter
import com.example.mod_home.databinding.FragmentHotBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.orhanobut.logger.Logger
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.bean.HotBean

class HotFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentHotBinding>() {
    private lateinit var mAdapter: HotAdapter
    private var mList = arrayListOf<HotBean>()

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
            getData(mCurrentPage, size)
        }
        mViewBinding.refreshLayout.setOnLoadMoreListener {
            mIsRefresh = false
            mCurrentPage++
            Logger.e("----->${mCurrentPage}")
            getData(mCurrentPage, size)
        }
    }

    override fun onLazyLoadData() {
        getData(1, size)
    }

    private fun getData(page: Int, size: Int) {
        mViewModel.getHotList(page, size).observe(this) {
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
                    mViewBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                }
            }
        }
    }

    private fun initRv() {
        mAdapter = HotAdapter()
        mAdapter.isEmptyViewEnable = true
//        mAdapter.setItemAnimation(BaseQuickAdapter.AnimationType.SlideInBottom)
//        val helper = QuickAdapterHelper.Builder(mAdapter)
        //设置尾部加载更多
//            .setConfig(ConcatAdapter.Config.DEFAULT)
//            .build()
        mViewBinding.rv.adapter = mAdapter
    }
}