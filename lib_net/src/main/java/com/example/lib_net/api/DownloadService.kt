package com.example.lib_net.api

import com.xwl.common_base.response.BaseResponse
import com.xwl.common_lib.constants.UrlConstants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author  lxw
 * @date 2023/9/19
 * descripe
 */
interface DownloadService {
    /**
     * 用户注册
     */
    @Streaming
    @GET
    suspend fun downloadFile(
        @Header("RANGE") start: String,
        @Url url: String
    ): Response<ResponseBody>
}