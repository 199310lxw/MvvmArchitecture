package com.xwl.common_lib.utils

import androidx.fragment.app.FragmentActivity
import com.xwl.common_lib.dialog.MessageDialog
import com.xwl.common_lib.provider.LoginServiceProvider

/**
 * @author  lxw
 * @date 2023/10/12
 * descripe
 */
object LoginDialogUtil {
    fun show(context: FragmentActivity, msg: String) {
        val builder = MessageDialog.Builder()
        val dialog = builder.setTitleText("提示")
//            .setIsCancelVisible(false)
            .setCancelText("取消")
            .setContentText(msg)
            .setConfirmText("是")
            .build()
        dialog.show(context.supportFragmentManager, "dialog")
        dialog.setOnItemClickListener(object : MessageDialog.OnItemClickListener {
            override fun onCancel() {

            }

            override fun onConfirm() {
                LoginServiceProvider.skipLoginActivity(context)
                dialog.dismiss()
            }

        })
    }
}