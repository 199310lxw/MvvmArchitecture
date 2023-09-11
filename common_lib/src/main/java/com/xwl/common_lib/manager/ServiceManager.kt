package com.xwl.common_lib.manager

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.xwl.common_lib.service.IArouterService

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
object ServiceManager {
    private var service: IArouterService = ARouter.getInstance().build("/app/arouterServiceImpl").navigation() as IArouterService

    fun getApplicationContext(): Context {
        return service.applicationContext
    }

}