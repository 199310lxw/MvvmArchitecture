package com.xwl.common_lib.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.xwl.common_lib.R
import com.xwl.common_lib.dialog.LoadingDialog

/**
 * @author  lxw
 * @date 2023/9/13
 * descripe
 */

private var loadingDialog: LoadingDialog? = null
/**
 * 打开等待框
 */
fun AppCompatActivity.showLoadingExt(message: String = "加载中...") {
    if (!this.isFinishing) {
        if(loadingDialog == null) {
            loadingDialog = LoadingDialog(this, R.style.loading_dialog)
        }
        loadingDialog?.setTitle(message)
        loadingDialog?.show()
    }
}
/**
 * 打开等待框
 */
fun Fragment.showLoadingExt(message: String = "加载中...") {
    if (!requireActivity().isFinishing) {
        if(loadingDialog == null) {
            loadingDialog = LoadingDialog(requireActivity(), R.style.loading_dialog)
        }
        loadingDialog?.setTitle(message)
        loadingDialog?.show()
    }
}
/**
 * 关闭等待框
 */
fun AppCompatActivity.dismissLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}

/**
 * 关闭等待框
 */
fun Fragment.dismissLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}
