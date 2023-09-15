package com.example.mod_home.ui.activity

import android.graphics.Color
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mod_home.databinding.ActivityHomeBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.ext.setUrlCircleBorder
import com.xwl.common_lib.provider.LoginServiceProvider
import com.xwl.common_lib.utils.AppExit

@Route(path = RoutMap.HOME_ACTIVITY_HOME)
class HomeActivity : BaseVmVbActivity<HomeViewModel,ActivityHomeBinding>() {
    private val url = "https://img0.baidu.com/it/u=1271469774,4048301316&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1694883600&t=55463d097c342c5b7b90857628b4c91f"
    override fun initView(savedInstanceState: Bundle?) {
        mViewBinding.img.setUrlCircleBorder(url,6f,Color.parseColor("#FF0000"))
         mViewBinding.btn.onClick {
             LoginServiceProvider.skipLoginActivity(this@HomeActivity)
         }
    }

    override fun onResume() {
        super.onResume()
        if(LoginServiceProvider.isLogin()) {
//            mViewBinding.tv.text = "用户已登陆"
        } else {
//            mViewBinding.tv.text = "用户未登录"
        }
    }

    override fun initData() {

    }

    override fun onBackPressed() {
        AppExit.onBackPressed(this)
    }
}