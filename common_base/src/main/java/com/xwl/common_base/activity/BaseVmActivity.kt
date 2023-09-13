package com.xwl.common_base.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.ext.dismissLoadingExt
import com.xwl.common_lib.ext.getVmClazz
import com.xwl.common_lib.ext.showLoadingExt

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
abstract class BaseVmActivity<VM: BaseViewModel>:BaseActivity() {
    protected lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(setLayout() != null) setContentView(setLayout()) else setContentView(layoutId())
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        initView(savedInstanceState)
        initData()
        registerUiChange()
    }

    private fun createViewModel():VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }

    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observeInActivity(this){
            if(it) {
                showLoadingExt("正在请求中,请稍后...")
            } else {
                dismissLoadingExt()
            }
        }
    }

    abstract fun setLayout(): View?

    abstract fun layoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initData()
}