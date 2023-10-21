package com.example.mod_home.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import com.chad.library.adapter.base.QuickAdapterHelper
import com.example.mod_home.adapters.HomeAdapter
import com.example.mod_home.adapters.HomeSortAdapter
import com.example.mod_home.databinding.FragmentHomeBinding
import com.example.mod_home.viewmodel.HomeViewModel
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
        mViewBinding.refreshLayout.setOnRefreshListener {
            getData(false)
        }
    }

    private fun getListData() {
        val lists = arrayListOf<HotBean>()
//        for (index in 0 until 4) {
//            val bean = HotBean()
//            bean.mainPicUrl =
//                "https://img1.baidu.com/it/u=174314668,4200091718&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=800"
//            bean.title = "hhhhhhhhh"
//            lists.add(bean)
//        }
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
        mViewBinding.banner.setOnBannerListener { data, position ->

        }
    }

    override fun onLazyLoadData() {

    }

    private fun getData(showloading: Boolean = true) {
        getListData()
        mViewModel.getSortList(showloading).observe(this) {
            it?.let {
                getSortData(it)
            }.also {
                mViewModel.getBannerList(showloading).observe(this) { data ->
                    data?.let {
                        initBanner(it)
                        mViewBinding.refreshLayout.finishRefresh()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}