package com.example.mod_home.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.mod_home.adapters.HotAdapter
import com.example.mod_home.databinding.FragmentHotBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.activity.WebViewActivity
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.constants.UrlConstants
import com.xwl.common_lib.ext.onClick

class HotFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentHotBinding>() {
    private lateinit var mAdapter: HotAdapter
    private var mList = arrayListOf<String>()
    companion object {
        fun newInstance() = HotFragment()
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {

    }

    override fun onLazyLoadData() {
        mList.add("AAAAAAA")
        mList.add("BBBBBBB")
        mList.add("CCCCCCC")
        mList.add("DDDDDDD")
        mList.add("EEEEEEE")
        mAdapter = HotAdapter()
        mAdapter.submitList(mList)
        mViewBinding.rv.adapter = mAdapter
    }
}