package com.xwl.common_lib.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author  lxw
 * @date 2023/10/12
 * descripe
 */
@Parcelize
data class User(
    var phone: String = "",
    var username: String = "",
    var nickname: String = "",
    var icon: String = "",
    var session: String = "",
    var sex: String = "",
    var birthday: String = "",
    var signature: String = "",
    var password: String?,
) : Parcelable {
    fun getName(): String? {
        return if (!nickname.isNullOrEmpty()) {
            nickname
        } else {
            username
        }
    }
}
