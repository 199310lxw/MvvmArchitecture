package com.example.mod_home.ui.fragment

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.QuickAdapterHelper
import com.example.mod_home.adapters.HotAdapter
import com.example.mod_home.databinding.FragmentHotBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.bean.HotBean

class HotFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentHotBinding>() {
    private lateinit var mAdapter: HotAdapter
    private var mList = arrayListOf<HotBean>()

    private var mCurrentPage = 1
    private var mIsRefresh = true

    companion object {
        fun newInstance() = HotFragment()
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        initRv()
        mViewBinding.refreshLayout.setOnRefreshListener {
            mIsRefresh = true
            mCurrentPage = 1
            getData(mCurrentPage)
        }
        mViewBinding.refreshLayout.setOnLoadMoreListener {
            mIsRefresh = false
            mCurrentPage++
            getData(mCurrentPage)
        }
    }

    override fun onLazyLoadData() {
        getData(1)
    }

    private fun getData(page: Int) {
        mViewModel.getHotList(page).observe(this) {

            it?.let {
                if (mIsRefresh) {
                    if (it.isEmpty()) {
                        mAdapter.setEmptyViewLayout(
                            mContext,
                            com.xwl.common_lib.R.layout.view_empty_data
                        )
                    }
                    mAdapter.submitList(it)
                    mViewBinding.refreshLayout.finishRefresh()
                } else {
                    mAdapter.addAll(it)
                    mViewBinding.refreshLayout.finishLoadMore()
                }
            }
        }
    }

    private fun initRv() {
        mAdapter = HotAdapter()
//        mAdapter.setItemAnimation(BaseQuickAdapter.AnimationType.SlideInBottom)
        val helper = QuickAdapterHelper.Builder(mAdapter)
            //设置尾部加载更多
//            .setConfig(ConcatAdapter.Config.DEFAULT)
            .build()
        mViewBinding.rv.adapter = helper.adapter
    }
}