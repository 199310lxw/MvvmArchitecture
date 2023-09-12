package com.xwl.common_lib.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @author  lxw
 * @date 2023/9/12
 * descripe
 */
interface ILoginService: IProvider {
    /**
     * 是否登陆
     */
    fun isLogin() : Boolean

    /**
     * 跳转登陆页
     */
    fun skipLoginActivity(context: Context)
}