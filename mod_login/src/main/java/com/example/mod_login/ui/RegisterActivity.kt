package com.example.mod_login.ui

import android.os.Bundle
import com.example.mod_login.databinding.ActivityRegisterBinding
import com.example.mod_login.viewmodel.LoginViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.ext.onClick

/**
 * @author  lxw
 * @date 2023/9/22
 * descripe
 */
class RegisterActivity: BaseVmVbActivity<LoginViewModel,ActivityRegisterBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mViewBinding.btnRegister.onClick {
            registerRequest()
        }
    }

    override fun initData() {

    }

    private fun registerRequest() {
        val map = HashMap<String,Any>()
        map["phone"] = mViewBinding.editPhone.text?.trim().toString()
        map["password"] = mViewBinding.editPassWord.text?.trim().toString()
        mViewModel.register(map).observe(this) {
            TipsToast.showTips(it)
        }
    }
}