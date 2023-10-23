package com.xwl.common_base.utils

import androidx.fragment.app.FragmentActivity
import com.xwl.common_base.dialog.MessageDialog
import com.xwl.common_lib.provider.LoginServiceProvider

/**
 * @author  lxw
 * @date 2023/10/12
 * descripe
 */
object LoginDialogUtil {
    fun show(context: FragmentActivity, msg: String) {
        MessageDialog.Builder(context)
            .setContent(msg)
            .setCancelText("取消")
            .setConfirmText("是")
            .setOnCancelClickListener { }
            .setOnConfirmClickListener {
                LoginServiceProvider.skipLoginActivity(context)
            }.show()
    }
}