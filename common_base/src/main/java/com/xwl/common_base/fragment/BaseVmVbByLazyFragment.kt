package com.xwl.common_base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.ext.dismissLoadingExt
import com.xwl.common_lib.ext.getVmClazz
import com.xwl.common_lib.ext.inflateBindingWithGeneric
import com.xwl.common_lib.ext.showLoadingExt

/**
 * @author  lxw
 * @date 2023/9/13
 * descripe
 */
abstract class BaseVmVbByLazyFragment<VM: BaseViewModel,VB: ViewBinding>: BaseLazyFragment() {
    protected lateinit var mViewModel: VM
    protected lateinit var mViewBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = inflateBindingWithGeneric(inflater,container,false)
        mViewModel = createViewModel()
        registerUiChange()
        initView(savedInstanceState,mViewBinding.root)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun createViewModel():VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }


    private fun registerUiChange() {
        mViewModel.loadingChange.showDialog.observeInFragment(this) {
            if(it) {
                showLoadingExt()
            } else {
                dismissLoadingExt()
            }
        }
    }

    /**
     * 初始化视图
     */
    protected abstract fun initView(savedInstanceState: Bundle?, view: View?)

}