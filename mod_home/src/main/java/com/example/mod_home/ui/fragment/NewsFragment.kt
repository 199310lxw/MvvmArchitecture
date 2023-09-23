package com.example.mod_home.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mod_home.R
import com.example.mod_home.databinding.FragmentHomeBinding
import com.example.mod_home.databinding.FragmentNewsBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.activity.WebViewActivity
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.constants.UrlConstants
import com.xwl.common_lib.ext.onClick

class NewsFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentNewsBinding>() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
       mViewBinding.tvNews.onClick {
           WebViewActivity.start(mContext,UrlConstants.BASE_URL,"首页")
//           WebViewActivity.start(mContext,"https://github.com/","首页")
       }
    }

    override fun onLazyLoadData() {

    }
}