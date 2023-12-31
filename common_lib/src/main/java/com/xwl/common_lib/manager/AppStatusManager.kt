package com.xwl.common_lib.manager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.orhanobut.logger.Logger

/**
 * @author  lxw
 * @date 2023/9/15
 * descripe
 */
object AppStatusManager {
    var startActivityNum = 0

    fun register(application: Application, listener: IAppStatusListener) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                ActivityManager.addActivity(activity)
                Logger.i("${activity.localClassName}：onActivityCreated")
            }

            override fun onActivityStarted(activity: Activity) {
                Logger.i("${activity.localClassName}：onActivityStarted")
                startActivityNum++
                if (startActivityNum == 1) {
                    listener.onFront()
                }

            }
            

            override fun onActivityResumed(activity: Activity) {
                Logger.i("${activity.localClassName}：onActivityResumed")
            }

            override fun onActivityPaused(activity: Activity) {
                Logger.i("${activity.localClassName}：onActivityPaused")
            }

            override fun onActivityStopped(activity: Activity) {
                Logger.i("${activity.localClassName}：onActivityStopped")
                startActivityNum--
                if (startActivityNum == 0) {
                    listener.onBack()
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Logger.i("${activity.localClassName}：onActivitySaveInstanceState")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Logger.i("${activity.localClassName}：onActivityDestroyed")
                ActivityManager.removeActivity(activity)
            }
        })
    }

    interface IAppStatusListener {
        fun onBack()
        fun onFront()
    }
}