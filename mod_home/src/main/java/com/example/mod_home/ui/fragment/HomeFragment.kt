package com.example.mod_home.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mod_home.R
import com.example.mod_home.adapters.MyAdapter
import com.example.mod_home.databinding.FragmentHomeBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.dialog.TipsToast

class HomeFragment : BaseVmVbByLazyFragment<HomeViewModel,FragmentHomeBinding>() {
    private lateinit var myAdapter: MyAdapter
    private val lists = arrayListOf<String>()
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

    }

    override fun onLazyLoadData() {

    }
}