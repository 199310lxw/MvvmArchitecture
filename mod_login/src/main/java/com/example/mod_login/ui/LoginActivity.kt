package com.example.mod_login.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.example.mod_login.databinding.ActivityLoginBinding
import com.example.mod_login.viewmodel.LoginViewModel
import com.orhanobut.logger.Logger
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.provider.UserServiceProvider

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
@Route(path = RoutMap.LOGIN_ACTIVITY_LOGIN)
class LoginActivity : BaseVmVbActivity<LoginViewModel, ActivityLoginBinding>() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun initView(savedInstanceState: Bundle?) {
//        mViewBinding.imgFingure.onClick {
//            BiometricUtils.startRecognize(this@LoginActivity, recognizeResult = {code, msg ->
//                if(code == 0) {
//                    TipsToast.showTips("指纹识别成功")
//                } else if (code == -1) {
//                    TipsToast.showTips("指纹识别有误，请重试")
//                } else {
//                    TipsToast.showTips(msg)
//                }
//            })

//            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
//        }

        mViewBinding.btnLogin.onClick {
            val phone = mViewBinding.editPhone.text.toString().trim()
            val password = mViewBinding.editPassword.text.toString().trim()
            login(phone, password)
        }
    }

    private fun login(phone: String, password: String) {
        mViewModel.login(phone, password).observe(this) {
            it?.let {
                Logger.e("${it.username} ---${it.session}---${it.nickname}")
                UserServiceProvider.saveUserInfo(it)
                if (UserServiceProvider.getUserInfo() != null) {
                    Logger.e(UserServiceProvider.getUserInfo()?.username.toString())
                } else {
                    Logger.e("11111111111111111111111111")
                }

                ARouter.getInstance().build(RoutMap.HOME_ACTIVITY_HOME)
                    .navigation(this@LoginActivity, object : NavCallback() {
                        override fun onArrival(postcard: Postcard?) {
                            finish()
                        }
                    })
            }
        }
    }


    override fun initData() {
    }
}