package com.xwl.common_lib.utils

/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
object ClickUtil {
    private const val FAST_CLICK_DELAY_TIME = 600
    private var lastClickTime: Long = 0

    fun isFastClick(): Boolean {
        var flag = true
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - lastClickTime >= FAST_CLICK_DELAY_TIME) {
            flag = false
        }
        lastClickTime = currentClickTime
        return flag
    }

}