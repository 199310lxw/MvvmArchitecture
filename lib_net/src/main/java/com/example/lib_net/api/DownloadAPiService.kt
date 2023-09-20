package com.example.lib_net.api


import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author  lxw
 * @date 2023/9/19
 * descripe
 */
interface DownloadAPiService {
    /**
     * 用户注册
     */
    @Streaming
    @GET
    suspend fun downloadFile(
        @Header("RANGE") range: String,
        @Url url: String
    ): Response<ResponseBody>
}