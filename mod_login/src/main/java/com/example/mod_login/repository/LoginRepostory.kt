package com.example.mod_login.repository

import com.example.lib_net.api.ApiManager
import com.example.lib_net.repository.BaseRepository

/**
 * @author  lxw
 * @date 2023/9/14
 * descripe
 */
class LoginRepostory: BaseRepository() {
    /**
     * 注册
     */
    suspend fun register(map: Map<String,Any>,loadingDialog:(Boolean) -> Unit): Any? {
        return requestResponse(loadingDialog = loadingDialog) {
            ApiManager.api.register(map)
        }
    }
}