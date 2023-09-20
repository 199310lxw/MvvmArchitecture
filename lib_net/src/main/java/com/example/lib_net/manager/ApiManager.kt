package com.example.lib_net.manager

import com.example.lib_net.api.Api
import com.example.lib_net.api.ApiService
import com.example.lib_net.api.DownloadAPiService

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
object ApiManager {
    val api by lazy { Api.create(ApiService::class.java) }
    val downloadApi by lazy { Api.create(DownloadAPiService::class.java) }
}