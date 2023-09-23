package com.example.mod_home.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.mod_home.databinding.FragmentHomeBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.dialog.TipsToast

class HomeFragment : BaseVmVbByLazyFragment<HomeViewModel,FragmentHomeBinding>() {
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
        val map = HashMap<String,Any>()
        map["username"] = "fasasdasfafasd"
        map["password"] = "fasf123456"
        map["repassword"] = "fasf123456"
        mViewModel.register(map).observe(this) {
            TipsToast.showTips(it)
        }
    }
    override fun initView(savedInstanceState: Bundle?, view: View?) {
//        mViewBinding.tv.onClick {
//            activity?.let { it1 -> WebViewActivity.start(it1,"https://blog.csdn.net/gqg_guan/article/details/132588818","") }
//        }
        initRv()
    }

    override fun onLazyLoadData() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}