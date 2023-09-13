package com.example.mod_home.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mod_home.databinding.ActivityHomeBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.provider.LoginServiceProvider
import com.xwl.common_lib.utils.AppExit

@Route(path = RoutMap.HOME_ACTIVITY_HOME)
class HomeActivity : BaseVmVbActivity<HomeViewModel,ActivityHomeBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
         mViewBinding.btn.onClick {
             LoginServiceProvider.skipLoginActivity(this@HomeActivity)
         }
    }

    override fun onResume() {
        super.onResume()
        if(LoginServiceProvider.isLogin()) {
            mViewBinding.tv.text = "用户已登陆"
        } else {
            mViewBinding.tv.text = "用户未登录"
        }
    }

    override fun initData() {

    }

    override fun onBackPressed() {
        AppExit.onBackPressed(this)
    }
}