package com.xwl.common_lib.bean

/**
 * @author  lxw
 * @date 2023/10/7
 * descripe
 */
data class CourseListBean(
    var id: Int,
    var name: String = "",
    var videoUrl: String,
    var posterImgUrl: String,
    var bgColor: String
) {
}
