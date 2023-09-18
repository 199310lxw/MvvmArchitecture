package com.example.lib_net.repository

import com.example.lib_net.error.ApiException
import com.xwl.common_base.response.BaseResponse
import com.xwl.common_lib.dialog.LoadingDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

/**
 * @author  lxw
 * @date 2023/9/14
 * descripe
 */
open class BaseRepository {
    suspend fun <T> requestResponse(loadingDialog:(Boolean) -> Unit,requestCall: suspend() -> BaseResponse<T>?): T? {
        val response =  withContext(Dispatchers.IO) {
//            withContext(Dispatchers.Main) {
//                loadingDialog.invoke(true)
//            }

           withTimeout(8 * 1000) {
               requestCall()
           }
        } ?: return null

//         withContext(Dispatchers.Main) {
//            loadingDialog.invoke(false)
//        }
        if(!response.isSuccess()){
           throw ApiException(response.errorCode,response.errorMsg)
        }
        return response.data
    }
}