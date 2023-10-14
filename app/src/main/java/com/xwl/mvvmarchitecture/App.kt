package com.xwl.mvvmarchitecture

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.databinding.ktx.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.Logger
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.manager.AppStatusManager

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
class App : Application() {


    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context
        fun getApplicationContext(): Context {
            return mContext
        }
    }

    init {
        mContext = this
    }

    override fun onCreate() {
        super.onCreate()
        Logger.init("lxw")
        initArouter()
        initMMKv()
        registerAppStatus()
        TipsToast.init(this)
    }

    private fun registerAppStatus() {
        AppStatusManager.register(this, object : AppStatusManager.IAppStatusListener {
            override fun onBack() {
                Logger.i("当前应用在后台")
            }

            override fun onFront() {
                Logger.i("当前应用在前台")
            }
        })
    }

    /**
     * 初始化Arouter
     */
    private fun initArouter() {
        if (isDebug(this)) {    // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog() // Print log
            ARouter.openDebug() // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this)
    }

    private fun initMMKv() {
        val rootDir: String = MMKV.initialize(this)
        MMKV.setLogLevel(
            if (BuildConfig.DEBUG) {
                MMKVLogLevel.LevelDebug
            } else {
                MMKVLogLevel.LevelError
            }
        )
        Logger.d("mmkv root: $rootDir")
    }

    /**
     * 是否debug模式
     *
     * @param context
     * @return
     */
    private fun isDebug(context: Context): Boolean {
        return context.applicationInfo != null &&
                context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }
}