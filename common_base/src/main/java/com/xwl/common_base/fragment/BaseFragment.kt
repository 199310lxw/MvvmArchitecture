package com.xwl.common_base.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwl.common_lib.ext.dismissLoadingExt
import com.xwl.common_lib.ext.showLoadingExt

/**
 * @author  lxw
 * @date 2023/9/13
 * descripe
 */
abstract class BaseFragment: Fragment() {
    protected var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    /**
     * 初始化视图
     */
    protected abstract fun initView(savedInstanceState: Bundle?, view: View?)

    protected fun showLoadingDialog(){
        showLoadingExt()
    }
    protected fun dismissLoadingDialog(){
        dismissLoadingExt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissLoadingDialog()
        mContext = null
    }
}