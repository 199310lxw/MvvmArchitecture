package com.xwl.common_lib.utils

import android.content.Context
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import androidx.annotation.RequiresApi
import com.orhanobut.logger.Logger

/**
 * @author  lxw
 * @date 2023/9/19
 * descripe 指纹识别工具类
 */
object BiometricUtils {
    private lateinit var mBiometricPrompt: BiometricPrompt
    private var mCancellationSignal: CancellationSignal? = null
    private var mAuthenticationCallback: BiometricPrompt.AuthenticationCallback? = null

    @RequiresApi(Build.VERSION_CODES.P)
    fun startRecognize(context: Context,recognizeResult: (Int,String) -> Unit) {
        mBiometricPrompt = BiometricPrompt.Builder(context)
            .setTitle("指纹验证")
            .setNegativeButton("取消",context.mainExecutor) { dialog, which ->
                Logger.e("cancel is clicked")
            }
            .build()

        mCancellationSignal = CancellationSignal()
        mCancellationSignal!!.setOnCancelListener {
            Logger.e("cancel")
        }
        mAuthenticationCallback = object: BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                Logger.e("onAuthenticationError:${errorCode}:${errString}")
                recognizeResult.invoke(errorCode,errString.toString())
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Logger.e("onAuthenticationFailed")
                recognizeResult.invoke(-1,"onAuthenticationFailed")
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                if (result != null) {
                    recognizeResult.invoke(0,"onAuthenticationSucceeded")
                    Logger.e("onAuthenticationSucceeded:${result.authenticationType}: ${result.cryptoObject}")
                }
            }
        }
        mBiometricPrompt.authenticate(mCancellationSignal!!,context.mainExecutor,
            mAuthenticationCallback as BiometricPrompt.AuthenticationCallback
        )
    }
}