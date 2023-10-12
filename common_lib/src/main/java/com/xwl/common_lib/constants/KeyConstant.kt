package com.xwl.common_lib.constants

import android.os.Environment

/**
 * @author  lxw
 * @date 2023/9/12
 * descripe
 */
object KeyConstant {
    const val KEY_SESSSION = "key_session"
    const val KEY_USER_PHONE = "key_user_phone"
    const val KEY_USER_HEADURL = "key_user_headurl"
    const val KEY_USER_NAME = "key_user_name"
    const val KEY_URL = "key_url"
    const val KEY_COURSE_TYPE = "key_course_type"
    const val KEY_COURSE_VIDEO_URL = "key_course_video_url"
    const val KEY_COURSE_VIDEO_POSTER_URL = "key_course_video_poster_url"
    const val KEY_COURSE_VIDEO_NAME = "key_course_video_name"
    const val KEY_TITLE = "key_title"
    const val KEY_DOWNLOAD_URL = "key_download_url"
    const val KEY_DOWNLOAD_FILE = "key_download_path"
    const val KEY_DOWNLOAD_FILE_NAME = "key_download_fileName"
    val APP_ROOT_PATH =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/mvvmarchiteture"
    val APP_UPDATE_PATH = APP_ROOT_PATH + "/apk"

}