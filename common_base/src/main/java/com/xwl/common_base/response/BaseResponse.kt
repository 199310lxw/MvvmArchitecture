package com.xwl.common_base.response

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
data class BaseResponse<out T>(
    val data: T?,
    val code: Int = 0,//服务器状态码 这里0表示请求成功
    val msg: String = ""//错误信息
) {
    fun isSuccess(): Boolean {
        return  code == 0
    }
}