package com.xwl.common_lib.callback

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
interface IHttpCallBack<T> {
    fun onSuccess(result: T)
    fun onFailure(obj: Any?)
}