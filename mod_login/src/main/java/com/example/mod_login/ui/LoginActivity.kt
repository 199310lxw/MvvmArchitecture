package com.example.mod_login.ui

import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.example.mod_login.databinding.ActivityLoginBinding
import com.example.mod_login.viewmodel.LoginViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.R
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.provider.UserServiceProvider
import com.xwl.common_lib.utils.ScreenUtil.hideKeyboard

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

        mViewBinding.btnLoginCommit.onClick {
            val phone = mViewBinding.etLoginPhone.text.toString().trim()
            val password = mViewBinding.etLoginPassword.text.toString().trim()
            if (phone.length != 11) {
                mViewBinding.etLoginPhone.startAnimation(
                    AnimationUtils.loadAnimation(
                        this@LoginActivity,
                        R.anim.shake_anim
                    )
                )
                mViewBinding.btnLoginCommit.showError(300)
                TipsToast.showTips(R.string.common_phone_input_error)
                return@onClick
            }

            // 隐藏软键盘
            hideKeyboard(this@LoginActivity)
            login(phone, password)
        }
    }

    private fun login(phone: String, password: String) {
//        mViewBinding.btnLoginCommit.showProgress()
        mViewModel.login(phone, password).observe(this) {
            it?.let {
                UserServiceProvider.saveUserInfo(it)
                mViewBinding.btnLoginCommit.showSucceed()
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
        mViewModel.error.observe(this) {
            mViewBinding.btnLoginCommit.showError(300)
        }
    }
}