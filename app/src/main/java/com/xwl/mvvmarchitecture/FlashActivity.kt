package com.xwl.mvvmarchitecture

import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.viewmodel.EmptyViewModel
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.ext.gone
import com.xwl.common_lib.ext.onClick
import com.xwl.mvvmarchitecture.databinding.ActivityFlashBinding
import kotlinx.coroutines.flow.flow

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
class FlashActivity : BaseVmVbActivity<EmptyViewModel, ActivityFlashBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mViewBinding.lottieAnimationView.setAnimation("no_network.json")
        object: CountDownTimer(3000,1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                ARouter.getInstance().build(RoutMap.HOME_ACTIVITY_HOME)
                    .navigation(this@FlashActivity, object : NavCallback() {
                        override fun onArrival(postcard: Postcard?) {
                            finish()
                        }
                    })
            }
        }.start()
    }

    override fun initData() {

    }

    override fun onStop() {
        super.onStop()
        mViewBinding.lottieAnimationView.pauseAnimation()
    }
}