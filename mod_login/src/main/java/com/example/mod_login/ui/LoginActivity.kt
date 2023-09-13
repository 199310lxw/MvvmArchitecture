package com.example.mod_login.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.example.mod_login.databinding.ActivityLoginBinding
import com.example.mod_login.viewmodel.LoginViewModel
import com.orhanobut.logger.Logger
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.toast.TipsToast
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.manager.ActivityManager
import com.xwl.common_lib.manager.UserManager
import com.xwl.common_lib.utils.SharedPreferenceUtil


/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
@Route(path = RoutMap.LOGIN_ACTIVITY_LOGIN)
class LoginActivity : BaseVmVbActivity<LoginViewModel,ActivityLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        val map = HashMap<String,Any>()
        map["username"] = "mrliudasfafasd"
        map["password"] = "mrliu123456"
        map["repassword"] = "mrliu123456"
        mViewBinding.tv.onClick {
            mViewModel.register(map)?.observe(this){
                  it?.let {
                      TipsToast.showTips(it)
                  }
            }
        }
    }
}