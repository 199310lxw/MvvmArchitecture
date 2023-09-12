package com.xwl.mvvmarchitecture

import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.viewmodel.EmptyViewModel
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.ext.gone
import com.xwl.common_lib.ext.onClick
import com.xwl.mvvmarchitecture.databinding.ActivityMainBinding

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
class FlashActivity : BaseVmVbActivity<EmptyViewModel,ActivityMainBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mViewBinding.tv.gone()
         mViewBinding.btn.onClick {
             ARouter.getInstance().build(RoutMap.HOME_ACTIVITY_HOME)
                 .navigation(this@FlashActivity, object : NavCallback() {
                     override fun onArrival(postcard: Postcard?) {
//                         finish()
                     }
                 })
         }
    }

    override fun initData() {

    }
}