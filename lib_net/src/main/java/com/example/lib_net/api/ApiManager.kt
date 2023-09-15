package com.example.lib_net.api

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
object ApiManager {
    val api by lazy { Api.create(ApiService::class.java)}
}