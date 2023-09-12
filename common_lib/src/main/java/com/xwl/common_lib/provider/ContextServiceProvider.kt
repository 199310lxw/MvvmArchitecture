package com.xwl.common_lib.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.Logger
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.service.IContextService

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
object ContextServiceProvider {
    @Autowired(name = RoutMap.APP_SERVICE_CONTEXT)
    lateinit var service: IContextService

    init {
        ARouter.getInstance().inject(this)
    }

    fun getApplicationContext(): Context {
        return service.applicationContext
    }
}