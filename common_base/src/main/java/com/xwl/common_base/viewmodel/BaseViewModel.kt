package com.xwl.common_base.viewmodel

import androidx.lifecycle.ViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
abstract class BaseViewModel: ViewModel() {
    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框 因为需要跟网络请求显示隐藏loading配套才加的，不然我加他个鸡儿加
     */
    inner class UiLoadingChange {
        //显示加载框
        val showDialog by lazy { UnPeekLiveData<String>() }
        //隐藏
        val dismissDialog by lazy { UnPeekLiveData<Boolean>() }
    }


}