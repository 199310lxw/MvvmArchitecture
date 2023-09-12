package com.xwl.common_lib.manager

import android.app.Activity
import android.content.Context
import android.os.Build

/**
 * @author  lxw
 * @date 2023/9/12
 * descripe
 */
object ActivityManager {
    private val tasks = mutableListOf<Activity>()

    /**
     * 添加activity
     */
    fun addActivity(activity: Activity) {
        tasks.add(activity)
    }

    /**
     * 移除activity
     */
    fun removeActivity(activity: Activity) {
        tasks.remove(activity)
    }

    /**
     * 当前的activity
     */
    fun currentActivity():Activity {
        return tasks.last()
    }

    fun finishAllActivity(callback: (() -> Unit)? = null) {
        val it = tasks.iterator()
        while (it.hasNext()) {
            val item = it.next()
            it.remove()
            item.finish()
        }
        tasks.clear()
        callback?.invoke()
    }

    /**
     * 关闭其它activity
     */
    fun finishOtherActivity(clazz: Class<out Activity>, callback: (() ->Unit)? = null) {
        val it = tasks.iterator()
        while (it.hasNext()) {
            val item = it.next()
            if (item::class.java != clazz) {
                it.remove()
                item.finish()
            }
        }
        callback?.invoke()
    }


    /**
     * 关闭activity
     */
    fun finishActivity(clazz: Class<out Activity>) {
        val it = tasks.iterator()
        while (it.hasNext()) {
            val item = it.next()
            if (item::class.java == clazz) {
                it.remove()
                item.finish()
                break
            }
        }
    }

    /**
     * activity是否在栈中
     */
    fun isActivityExistsInStack(clazz: Class<out Activity>?): Boolean {
        if (clazz != null) {
            for (task in tasks) {
                if (task::class.java == clazz) {
                    return true
                }
            }
        }
        return false
    }
}