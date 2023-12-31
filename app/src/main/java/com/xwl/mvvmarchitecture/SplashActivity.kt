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
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.dialog.AgreeMentDialog
import com.xwl.common_base.viewmodel.EmptyViewModel
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.manager.MMKVAction
import com.xwl.mvvmarchitecture.databinding.ActivityFlashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
                delay(2000)
                flow {
                    emit(true)
                }.flowOn(Dispatchers.IO)
                    .collect {
                        if (MMKVAction.getDefaultMKKV()
                                .decodeBool(KeyConstant.KEY_AGREEMENT_STATUS, false)
                        ) {
                            toHome()
                        } else {
                            showAgreementDialog()
                        }
                    }
            }
        }
    }

    fun toHome() {
        ARouter.getInstance().build(RoutMap.HOME_ACTIVITY_HOME)
            .navigation(this@SplashActivity, object : NavCallback() {
                override fun onArrival(postcard: Postcard?) {
                    finish()
                }
            })
    }

    fun toBasics() {
        ARouter.getInstance().build(RoutMap.BASIC_ACTIVITY_MAIN)
            .navigation(this@SplashActivity, object : NavCallback() {
                override fun onArrival(postcard: Postcard?) {
                    finish()
                }
            })
    }

    /**
     * 隐私政策弹框
     */
    private fun showAgreementDialog() {
        AgreeMentDialog.Builder(this@SplashActivity)
            .setAgreeClickListener {
                MMKVAction.getDefaultMKKV().encode(KeyConstant.KEY_AGREEMENT_STATUS, true)
                toHome()
            }
            .setDisAgreeClickListener {
                MMKVAction.getDefaultMKKV().encode(KeyConstant.KEY_AGREEMENT_STATUS, false)
                toBasics()
            }
            .show()
    }

    override fun initData() {

    }

    override fun onStop() {
        super.onStop()
//        mViewBinding.lottieAnimationView.pauseAnimation()
    }
}