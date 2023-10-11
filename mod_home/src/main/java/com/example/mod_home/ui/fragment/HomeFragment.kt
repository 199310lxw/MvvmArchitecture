package com.example.mod_home.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.mod_home.databinding.FragmentHomeBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.bean.BannerBean
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.ext.setUrlRound
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator


class HomeFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentHomeBinding>() {
    private var dataLists: ArrayList<String> = arrayListOf()

    companion object {
        fun newInstance() = HomeFragment()
    }

    private fun initRv() {
//        mContext?.let { myAdapter?.setEmptyView(it) }
//        myAdapter.submitList(dataLists)
//        mViewBinding.rv.adapter = myAdapter
    }

    private fun requestData() {
        val map = HashMap<String, Any>()
        map["username"] = "fasasdasfafasd"
        map["password"] = "fasf123456"
        map["repassword"] = "fasf123456"
        mViewModel.register(map).observe(this) {
            TipsToast.showTips(it)
        }
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        initRv()
    }

    fun initBanner(lists: ArrayList<BannerBean>) {
        mViewBinding.banner.setAdapter(object :
            BannerImageAdapter<BannerBean>(lists) {
            override fun onBindView(
                holder: BannerImageHolder,
                data: BannerBean,
                position: Int,
                size: Int
            ) {
                //图片加载自己实现
                holder.imageView.setUrlRound(data.url, 12)
//                Glide.with(holder.itemView)
//                    .load(data.url)
//                    .apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
//                    .into(holder.imageView)
            }
        }).addBannerLifecycleObserver(this).indicator = CircleIndicator(requireActivity())
    }

    override fun onLazyLoadData() {
        mViewModel.getBannerList().observe(this) {
            it?.let {
                initBanner(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}