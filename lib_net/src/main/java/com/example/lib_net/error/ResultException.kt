package com.example.lib_net.error

/**
 * @author  lxw
 * @date 2023/9/14
 * descripe
 */
open class ResultException(code: Int, msg: String, e: Throwable? = null) : Exception(e) {
    var errCode: Int = code
    var errMsg: String = msg
}