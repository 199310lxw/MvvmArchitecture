package com.xwl.common_lib.manager

import com.tencent.mmkv.MMKV

/**
 * @author  lxw
 * @date 2023/10/25
 * descripe
 */
object MMKVAction {
    fun getDefaultMKKV(): MMKV {
        return MMKV.defaultMMKV()
    }
}