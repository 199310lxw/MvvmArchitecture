package com.xwl.common_lib.utils

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.CallSuper
import com.xwl.common_lib.dialog.TipsToast
import kotlin.system.exitProcess

/**
 * APP退出监听
 */
object AppExit {

    private var preExit = false

    private val handler = Handler(Looper.getMainLooper()) {
        preExit = false
        true
    }

    @CallSuper
    fun onBackPressed(
        activity: Activity,
        tipCallback: () -> Unit = {
            TipsToast.showTips("再按一次退出程序")
        },
        exitCallback: () -> Unit = {}
    ) {
        if (!preExit) {
            preExit = true
            tipCallback()
            handler.sendEmptyMessageDelayed(0, 2000)
        } else {
            exitCallback()
            activity.finish()
            exitProcess(0)
        }
    }
}