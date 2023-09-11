package com.xwl.common_base.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.Ext.getVmClazz

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
abstract class BaseVmActivity<VM: BaseViewModel>:BaseActivity() {
    protected var mViewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(setLayout() != null) setContentView(setLayout()) else setContentView(layoutId())
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        initView(savedInstanceState)
        initData()
    }

    private fun createViewModel():VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }

    abstract fun setLayout(): View?

    abstract fun layoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()
}