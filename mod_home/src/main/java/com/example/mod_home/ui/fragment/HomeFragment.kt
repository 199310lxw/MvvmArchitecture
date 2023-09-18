package com.example.mod_home.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.mod_home.databinding.FragmentHomeBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.activity.WebViewActivity
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.ext.onClick

class HomeFragment : BaseVmVbByLazyFragment<HomeViewModel,FragmentHomeBinding>() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel


//    private fun initRv() {
//        myAdapter = MyAdapter()
//        myAdapter.isEmptyViewEnable = true
//        myAdapter.animationEnable = true
//        myAdapter.setEmptyViewLayout(this@HomeActivity,com.xwl.common_base.R.layout.view_empty_data)
//        mViewBinding.rv.adapter = myAdapter
//    }

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
        mViewBinding.tv.onClick {
            activity?.let { it1 -> WebViewActivity.start(it1,"https://blog.csdn.net/gqg_guan/article/details/132588818","") }
        }
    }

    override fun onLazyLoadData() {

    }
}