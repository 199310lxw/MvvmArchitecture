package com.example.mod_login.ui

import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mod_login.databinding.ActivityLoginBinding
import com.example.mod_login.viewmodel.LoginViewModel
import com.orhanobut.logger.Logger
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.utils.BiometricUtils


/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
@Route(path = RoutMap.LOGIN_ACTIVITY_LOGIN)
class LoginActivity : BaseVmVbActivity<LoginViewModel,ActivityLoginBinding>() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun initView(savedInstanceState: Bundle?) {
        mViewBinding.imgFingure.onClick {
            BiometricUtils.startRecognize(this@LoginActivity, recognizeResult = {code, msg ->
                if(code == 0) {
                    TipsToast.showTips("指纹识别成功")
                } else if (code == -1) {
                    TipsToast.showTips("指纹识别有误，请重试")
                } else {
                    TipsToast.showTips(msg)
                }
            })
        }
    }

    override fun initData() {
    }
}