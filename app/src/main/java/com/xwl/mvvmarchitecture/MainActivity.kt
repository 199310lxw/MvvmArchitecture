package com.xwl.mvvmarchitecture

import android.os.Bundle
import androidx.core.view.isVisible
import com.orhanobut.logger.Logger
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.Ext.gone
import com.xwl.common_lib.Ext.onClick
import com.xwl.common_lib.Ext.visible
import com.xwl.mvvmarchitecture.databinding.ActivityMainBinding

class MainActivity : BaseVmVbActivity<MainViewModel,ActivityMainBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mViewBinding.tv.gone()
         mViewBinding.btn.onClick {
             if(mViewBinding.tv.isVisible) {
                 mViewBinding.tv.gone()
             } else {
                 mViewBinding.tv.visible()
             }
         }
    }
}