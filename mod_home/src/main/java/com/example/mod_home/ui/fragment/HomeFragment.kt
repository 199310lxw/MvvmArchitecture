package com.example.mod_home.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
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
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.ext.setUrl
import com.xwl.common_lib.utils.ScreenUtil
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.transformer.AlphaPageTransformer


class HomeFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentHomeBinding>() {
    private lateinit var mSortAdapter: HomeSortAdapter

    companion object {
        fun newInstance() = HomeFragment()
    }

    private fun initRv() {
        mSortAdapter = HomeSortAdapter()
        mViewBinding.rv.adapter = mSortAdapter
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        initRv()
        getData()
        mViewBinding.refreshLayout.setOnMultiListener(object : OnMultiListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                getData(false)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
//                mViewBinding.refreshLayout.finishLoadMore()
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

    private fun initBanner(lists: ArrayList<BannerBean>) {
        val screenWidth = ScreenUtil.getScreenWidth()
        val params = LinearLayout.LayoutParams(screenWidth, screenWidth * 3 / 5)
        mViewBinding.banner.layoutParams = params
        mViewBinding.banner.setAdapter(object :
            BannerImageAdapter<BannerBean>(lists) {
            override fun onBindView(
                holder: BannerImageHolder,
                data: BannerBean,
                position: Int,
                size: Int
            ) {
                //图片加载自己实现
                holder.imageView.setUrl(data.url)
            }
        }).setPageTransformer(AlphaPageTransformer())
            .addBannerLifecycleObserver(this).indicator =
            RectangleIndicator(requireActivity())
        mViewBinding.banner.setOnBannerListener { _, _ ->

        }
    }

    override fun onLazyLoadData() {
        mViewModel.error.observe(this) {
            TipsToast.showTips(it)
            mViewBinding.refreshLayout.finishRefresh()
        }
    }

    private fun getData(showLoading: Boolean = true) {
        mViewModel.getSortList(showLoading).observe(this) {
            it?.let {
                mSortAdapter.submitList(it)
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