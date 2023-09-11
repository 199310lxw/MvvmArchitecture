package com.xwl.common_base.net

import com.xwl.common_lib.net.Api
import com.xwl.common_lib.net.ApiService

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
object ApiManager {
    val api by lazy { Api.create(ApiService::class.java)}
}