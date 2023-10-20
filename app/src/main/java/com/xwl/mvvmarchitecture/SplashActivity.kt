package com.xwl.mvvmarchitecture

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.viewmodel.EmptyViewModel
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.dialog.MessageDialog
import com.xwl.common_lib.dialog.TipsToast.showTips
import com.xwl.mvvmarchitecture.databinding.ActivityFlashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
class SplashActivity : BaseVmVbActivity<EmptyViewModel, ActivityFlashBinding>() {

    private var hasShowWarning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun initView(savedInstanceState: Bundle?) {

        //设置虚拟按键为全透明
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//        window.decorView
//            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.navigationBarColor = Color.TRANSPARENT

//        mViewBinding.lottieAnimationView.setAnimation("no_network.json")
        //设置虚拟按键为半透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            lifecycleScope.launchWhenResumed {
                flow {
//                   delay(2000)
                    emit(true)
                }.flowOn(Dispatchers.IO)
                    .collect {
                        ARouter.getInstance().build(RoutMap.HOME_ACTIVITY_HOME)
                            .navigation(this@SplashActivity, object : NavCallback() {
                                override fun onArrival(postcard: Postcard?) {
                                    finish()
                                }
                            })
                    }
            }
        }

    }

    override fun initData() {

    }

    private fun checkPermission() {
        XXPermissions.with(this@SplashActivity)
            .permission(Permission.WRITE_EXTERNAL_STORAGE)
            .permission(Permission.READ_MEDIA_IMAGES)
            .permission(Permission.READ_MEDIA_VIDEO)
            .permission(Permission.READ_MEDIA_AUDIO)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: List<String>, all: Boolean) {
                    if (all) {
                        lifecycleScope.launchWhenResumed {
                            flow {
//                                delay(2000)
                                emit(true)
                            }.flowOn(Dispatchers.IO)
                                .collect {
                                    ARouter.getInstance().build(RoutMap.HOME_ACTIVITY_HOME)
                                        .navigation(this@SplashActivity, object : NavCallback() {
                                            override fun onArrival(postcard: Postcard?) {
                                                finish()
                                            }
                                        })
                                }
                        }
                    } else {
                        if (!hasShowWarning) {
                            showWarningDialog()
                        }
                    }
                }

                override fun onDenied(permissions: List<String>, never: Boolean) {
                    if (never) {
                        showTips("请到设置界面手动授予读写内存权限")
//                        showWarningDialog()
                    }
                }
            })
    }

    private fun showWarningDialog() {
        hasShowWarning = true
        val dialog = MessageDialog.Builder()
            .setTitleText("提示")
            .setCancelText("不同意")
            .setConfirmText("同意")
            .setContentText("应用需要获取相关权限才能打开，是否同意开启相关权限？")
            .build()
        dialog.setOnItemClickListener(object : MessageDialog.OnItemClickListener {
            override fun onCancel() {
                finish()
            }

            override fun onConfirm() {
                dialog.dismiss()
                checkPermission()
            }

        })
        dialog.show(supportFragmentManager, "dialog")
    }

    override fun onStop() {
        super.onStop()
//        mViewBinding.lottieAnimationView.pauseAnimation()
    }
}