package com.example.mod_home.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import com.chad.library.adapter.base.QuickAdapterHelper
import com.example.mod_home.adapters.HomeAdapter
import com.example.mod_home.adapters.HomeSortAdapter
import com.example.mod_home.databinding.FragmentHomeBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.listener.OnMultiListener
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.bean.BannerBean
import com.xwl.common_lib.bean.HotBean
import com.xwl.common_lib.ext.setUrlRound
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.AlphaPageTransformer


class HomeFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentHomeBinding>() {

    private lateinit var mAdapter: HomeAdapter
    private lateinit var mSortAdapter: HomeSortAdapter

    companion object {
        fun newInstance() = HomeFragment()
    }

    private fun initRv() {
        mAdapter = HomeAdapter()
        mSortAdapter = HomeSortAdapter()
//        mSortAdapter.setHasStableIds(true)
        val helper = QuickAdapterHelper
            .Builder(mAdapter)
            .setConfig(ConcatAdapter.Config.DEFAULT)
            .build()
        helper.addBeforeAdapter(mSortAdapter)
        mViewBinding.rv.adapter = helper.adapter
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        initRv()
        getData()
        mViewBinding.refreshLayout.setOnMultiListener(object : OnMultiListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                getData(false)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mViewBinding.refreshLayout.finishLoadMore()
            }

            @SuppressLint("RestrictedApi")
            override fun onStateChanged(
                refreshLayout: RefreshLayout,
                oldState: RefreshState,
                newState: RefreshState
            ) {

            }

            override fun onHeaderMoving(
                header: RefreshHeader?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                maxDragHeight: Int
            ) {

            }

            override fun onHeaderReleased(
                header: RefreshHeader?,
                headerHeight: Int,
                maxDragHeight: Int
            ) {

            }

            override fun onHeaderStartAnimator(
                header: RefreshHeader?,
                headerHeight: Int,
                maxDragHeight: Int
            ) {

            }

            override fun onHeaderFinish(header: RefreshHeader?, success: Boolean) {

            }

            override fun onFooterMoving(
                footer: RefreshFooter?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                footerHeight: Int,
                maxDragHeight: Int
            ) {
                // 上拉加载时，保证吸顶头部不被推出屏幕。
                // 如果你本身就设置了吸顶偏移量，那么这里的offset计算你的偏移量加offset
                mViewBinding.scrollerLayout.stickyOffset = offset
            }

            override fun onFooterReleased(
                footer: RefreshFooter?,
                footerHeight: Int,
                maxDragHeight: Int
            ) {

            }

            override fun onFooterStartAnimator(
                footer: RefreshFooter?,
                footerHeight: Int,
                maxDragHeight: Int
            ) {

            }

            override fun onFooterFinish(footer: RefreshFooter?, success: Boolean) {
            }
        })
    }

    private fun getListData() {
        val lists = arrayListOf<HotBean>()
        for (index in 0 until 14) {
            val bean = HotBean()
            bean.posterUrl =
                "https://img2.baidu.com/it/u=3899920834,1058134886&fm=253&fmt=auto&app=138&f=JPEG?w=650&h=406"
            bean.title = "hhhhhhhhh"
            lists.add(bean)
        }
        mAdapter.submitList(lists)

    }

    private fun getSortData(sortList: ArrayList<HotBean>) {
        val lists = arrayListOf<Int>()
        lists.add(1)
        mSortAdapter.setData(sortList)
        mSortAdapter.submitList(lists)
    }


    private fun initBanner(lists: ArrayList<BannerBean>) {
        mViewBinding.banner.setAdapter(object :
            BannerImageAdapter<BannerBean>(lists) {
            override fun onBindView(
                holder: BannerImageHolder,
                data: BannerBean,
                position: Int,
                size: Int
            ) {
                //图片加载自己实现
                holder.imageView.setUrlRound(data.url, 20)
            }
        }).setBannerRound(20f)
            .setPageTransformer(AlphaPageTransformer())
            .addBannerLifecycleObserver(this).indicator =
            CircleIndicator(requireActivity())
        mViewBinding.banner.setOnBannerListener { _, _ ->

        }
    }

    override fun onLazyLoadData() {

    }

    private fun getData(showLoading: Boolean = true) {
        getListData()
        mViewModel.getSortList(showLoading).observe(this) {
            it?.let {
                getSortData(it)
            }.also {
                mViewModel.getBannerList(showLoading).observe(this) { data ->
                    data?.let { it1 ->
                        initBanner(it1)
                        mViewBinding.refreshLayout.finishRefresh()
                    }
                }
            }
        }
    }
}