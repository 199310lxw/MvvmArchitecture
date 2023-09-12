package com.xwl.common_base.response

import com.google.gson.annotations.SerializedName

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
data class BaseResponse<out T>(
    val data: T?,
    @SerializedName(value = "errorCode", alternate = ["code"])
    val errorCode: Int = 0,//服务器状态码 这里0表示请求成功
    @SerializedName(value = "errorMsg", alternate = ["msg"])
    val errorMsg: String = ""//错误信息
) {
    fun isSuccess(): Boolean {
        return  errorCode == 0
    }
}