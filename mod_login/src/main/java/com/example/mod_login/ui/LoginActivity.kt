package com.example.mod_login.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mod_login.databinding.ActivityLoginBinding
import com.example.mod_login.viewmodel.LoginViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.constants.RoutMap


/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
@Route(path = RoutMap.ACTIVITY_LOGIN)
class LoginActivity : BaseVmVbActivity<LoginViewModel,ActivityLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        mViewModel.getData()
    }
}