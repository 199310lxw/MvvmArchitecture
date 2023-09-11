package com.xwl.common_lib.net

import android.util.Log
import com.xwl.common_base.net.InjectHeaderInterceptor
import com.xwl.common_lib.constants.UrlConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
object Api {
    private val mRetrofit: Retrofit

    init{
        mRetrofit = Retrofit.Builder()
            .client(initOkHttpClient())
            .baseUrl(UrlConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    /**
     * 获取 apiService
     */
    fun <T> create(apiService: Class<T>): T {
        return mRetrofit.create(apiService)
    }

    /**
     * 初始化OkHttp
     */
    private fun initOkHttpClient(): OkHttpClient {
        val build = OkHttpClient.Builder()
            .connectTimeout(12, TimeUnit.SECONDS)
            .writeTimeout(12, TimeUnit.SECONDS)
            .readTimeout(12, TimeUnit.SECONDS)
        // 添加参数拦截器
        val interceptors = mutableListOf<Interceptor>()
        build.addInterceptor(InjectHeaderInterceptor())
        return build.build()
    }
}