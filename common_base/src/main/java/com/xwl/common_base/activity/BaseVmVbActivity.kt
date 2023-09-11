package com.xwl.common_base.activity

import android.view.View
import androidx.viewbinding.ViewBinding
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.Ext.inflateBindingWithGeneric

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
abstract class BaseVmVbActivity<VM: BaseViewModel,VB: ViewBinding>: BaseVmActivity<VM>() {
    protected lateinit var mViewBinding: VB
    override fun setLayout(): View? {
        mViewBinding = inflateBindingWithGeneric(layoutInflater)
        return mViewBinding.root
    }

    override fun layoutId(): Int = 0

}