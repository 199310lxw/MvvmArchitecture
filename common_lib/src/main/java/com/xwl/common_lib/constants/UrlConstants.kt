package com.xwl.common_lib.constants

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
object UrlConstants {
    //        const val BASE_URL = "http://192.168.1.43/"
    const val BASE_URL = "http://192.168.10.17/"

    const val URL_REGISTER = "server/user/register"//注册

    const val LOGIN_URL = "server/user/login/index.php"//登录

    const val UPDATE_USER_ICON_URL = "server/user/upload/uploadicon.php"//上传头像

    const val SAVE_USER_INFO_URL = "server/user/updateUserInfo.php"//上传用户信息

    const val APK_URL = "server/apk/mvvmarchitecture.apk"//应用更新地址

    const val VIDEO_LIST_URL = "server/home/video"//视频

    const val UPLOAD_FAVORITE_URL = "server/home/uploadFavorite.php"//收藏

    const val DISCOLLECT_FAVORITE_URL = "server/home/disCollectFavorite.php"//取消收藏

    const val COLLECT_LIST_URL = "server/home/getCollectList.php"//获取收藏列表

    const val CHECK_COLLECTED_URL = "server/home/checkCollected.php"//判断是否收藏了该内容

    const val BANNER_LIST_URL = "server/home/banner/"//首页banner

    const val SORT_LIST_URL = "server/home/sort/"//首页分类

    const val AGREENMENT_URL = "https://zhuanlan.zhihu.com/p/598904426"
}