package com.xwl.common_base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xwl.common_lib.ext.dismissLoadingExt
import com.xwl.common_lib.ext.showLoadingExt

/**
 * @author  lxw
 * @date 2023/9/13
 * descripe
 */
abstract class BaseFragment: Fragment() {
    protected lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }



    protected fun showLoadingDialog(){
        showLoadingExt()
    }
    protected fun dismissLoadingDialog(){
        dismissLoadingExt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissLoadingDialog()
    }
}