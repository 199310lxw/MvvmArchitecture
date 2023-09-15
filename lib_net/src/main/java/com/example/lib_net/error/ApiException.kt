package com.example.lib_net.error

/**
 * @author  lxw
 * @date 2023/9/14
 * descripe
 */
open class ApiException: Exception {
    var errCode: Int
    var errMsg: String
    constructor(code: Int,msg: String) {
        errCode = code
        errMsg  =  msg
    }
}