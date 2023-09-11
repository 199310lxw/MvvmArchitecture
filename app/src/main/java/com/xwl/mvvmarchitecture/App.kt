package com.xwl.mvvmarchitecture

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityCallback()
        initArouter()
    }

    /**
     * 初始化Arouter
     */
    private fun initArouter() {
        if (isDebug(this)) {    // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog() // Print log
            ARouter.openDebug() // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this)
    }

    /**
     * 是否debug模式
     *
     * @param context
     * @return
     */
    fun isDebug(context: Context): Boolean {
        return context.applicationInfo != null &&
                context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }

    fun registerActivityCallback() {
        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacks{
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }
}