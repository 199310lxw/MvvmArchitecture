package com.xwl.common_lib.constants

import android.os.Environment

/**
 * @author  lxw
 * @date 2023/9/12
 * descripe
 */
object KeyConstant {
    const val KEY_USER_PHONE = "user_phone"
    const val KEY_URL = "key_url"
    const val KEY_COURSE_TYPE = "key_course_type"
    const val KEY_TITLE = "key_title"
    const val KEY_DOWNLOAD_URL = "key_download_url"
    val APP_ROOT_PATH =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/mvvmarchiteture"
    val APP_UPDATE_PATH = APP_ROOT_PATH + "/apk"

}