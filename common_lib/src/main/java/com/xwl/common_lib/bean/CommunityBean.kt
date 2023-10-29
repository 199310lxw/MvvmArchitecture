package com.xwl.common_lib.bean

/**
 * @author  lxw
 * @date 2023/10/11
 * descripe
 */
data class CommunityBean(
    var id: Int = 0,
    var title: String = "",
    var author: String = "",
    var imgList: ArrayList<String>? = null
)
