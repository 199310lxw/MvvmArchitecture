package com.xwl.mvvmarchitecture

import android.os.Bundle
import android.os.CountDownTimer
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.orhanobut.logger.Logger
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.viewmodel.EmptyViewModel
import com.xwl.common_lib.constants.KeyConstant.APP_UPDATE_PATH
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.dialog.TipsToast.showTips
import com.xwl.mvvmarchitecture.databinding.ActivityFlashBinding

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
class FlashActivity : BaseVmVbActivity<EmptyViewModel, ActivityFlashBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mViewBinding.lottieAnimationView.setAnimation("no_network.json")
       checkPermission()
    }

    override fun initData() {

    }

    private fun checkPermission() {
        XXPermissions.with(this@FlashActivity)
            .permission(Permission.WRITE_EXTERNAL_STORAGE)
            .permission(Permission.READ_MEDIA_IMAGES)
            .permission(Permission.READ_MEDIA_VIDEO)
            .permission(Permission.READ_MEDIA_AUDIO)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: List<String>, all: Boolean) {
                    if(all) {
                        object: CountDownTimer(2000,1000) {
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
                }

                override fun onDenied(permissions: List<String>, never: Boolean) {
                    if (never) {
                        showTips("请到设置界面手动授予读写内存权限")
                    }
                }
            })

    }

    override fun onStop() {
        super.onStop()
        mViewBinding.lottieAnimationView.pauseAnimation()
    }
}