package com.example.mod_home.repostory

import com.example.lib_net.manager.ApiManager
import com.example.lib_net.repository.BaseRepository

/**
 * @author  lxw
 * @date 2023/9/14
 * descripe
 */
class HomeRepostory: BaseRepository() {
    /**
     * 注册
     */
    suspend fun register(map: Map<String,Any>,loadingDialog:(Boolean) -> Unit): Any? {
        return requestResponse(loadingDialog = loadingDialog) {
            ApiManager.api.register(map)
        }
    }
}