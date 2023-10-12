package com.example.mod_login.service

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mod_login.ui.LoginActivity
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.manager.ActivityManager
import com.xwl.common_lib.service.ILoginService
import com.xwl.common_lib.utils.SharedPreferenceUtil

/**
 * @author  lxw
 * @date 2023/9/12
 * descripe
 */
@Route(path = RoutMap.LOGIN_SERVICE_LOGIN)
class LoginService : ILoginService {
    override fun isLogin(): Boolean {
        val userPhone = SharedPreferenceUtil.getInstance().getString(KeyConstant.KEY_USER_PHONE, "")
        return userPhone != null && userPhone.isNotEmpty()
    }

    /**
     * 跳转登陆页
     */
    override fun skipLoginActivity(context: Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
        ActivityManager.finishOtherActivity(LoginActivity::class.java)
    }

    override fun init(context: Context?) {

    }
}