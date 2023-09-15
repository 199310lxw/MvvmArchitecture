package com.example.mod_home.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mod_home.databinding.ActivityHomeBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.provider.LoginServiceProvider
import com.xwl.common_lib.utils.AppExit

@Route(path = RoutMap.HOME_ACTIVITY_HOME)
class HomeActivity : BaseVmVbActivity<HomeViewModel,ActivityHomeBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
//         mViewBinding.emptyView.setAnimPath("empty_data.json")
         mViewBinding.btn.onClick {
             LoginServiceProvider.skipLoginActivity(this@HomeActivity)
         }
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