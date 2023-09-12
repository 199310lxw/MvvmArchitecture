package com.xwl.common_lib.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.service.ILoginService

/**
 * @author  lxw
 * @date 2023/9/12
 * descripe
 */
object LoginServiceProvider {
    @Autowired(name = RoutMap.LOGIN_SERVICE_LOGIN)
    lateinit var loginService: ILoginService


    init {
        ARouter.getInstance().inject(this)
    }

    /**
     * 是否登录
     * @return Boolean
     */
    fun isLogin(): Boolean {
        return loginService.isLogin()
    }

    /**
     * 跳转登录页
     * @param context
     */
    fun skipLoginActivity(context: Context) {
        loginService.skipLoginActivity(context)
    }
}