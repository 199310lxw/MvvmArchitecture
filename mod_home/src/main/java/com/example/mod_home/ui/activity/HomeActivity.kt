package com.example.mod_home.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mod_home.adapters.MyAdapter
import com.example.mod_home.databinding.ActivityHomeBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.provider.LoginServiceProvider
import com.xwl.common_lib.utils.AppExit

@Route(path = RoutMap.HOME_ACTIVITY_HOME)
class HomeActivity : BaseVmVbActivity<HomeViewModel,ActivityHomeBinding>() {
    private lateinit var myAdapter: MyAdapter
    override fun initView(savedInstanceState: Bundle?) {
        initRv()
         mViewBinding.btn.onClick {
             LoginServiceProvider.skipLoginActivity(this@HomeActivity)
         }
    }

    private fun initRv() {
        val lists = arrayListOf<String>()
        lists.add("aaaa")
        lists.add("bbbb")
        lists.add("cccc")
        lists.add("dddd")
        lists.add("eeee")
        myAdapter = MyAdapter()
        myAdapter.submitList(lists)
        mViewBinding.rv.adapter = myAdapter
    }

    override fun onResume() {
        super.onResume()
    }

    override fun initData() {

    }

    override fun onBackPressed() {
        AppExit.onBackPressed(this)
    }
}