package com.xwl.common_lib.manager

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * @author  lxw
 * @date 2023/9/15
 * descripe
 */
object AppStatusManager {
    var startActivityNum = 0 

    fun register(application: Application,listener: IAppStatusListener) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks{
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                ActivityManager.addActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                startActivityNum++
                if (startActivityNum == 1) {
                    listener.onFront()
                }
            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
                startActivityNum--
                if (startActivityNum == 0) {
                    listener.onBack()
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                ActivityManager.removeActivity(activity)
            }
        })
    }

    interface IAppStatusListener{
        fun onBack()
        fun onFront()
    }
}