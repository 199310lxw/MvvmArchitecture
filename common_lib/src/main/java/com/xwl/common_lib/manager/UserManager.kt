package com.xwl.common_lib.manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tencent.mmkv.MMKV
import com.xwl.common_lib.bean.User
import com.xwl.common_lib.constants.KeyConstant

/**
 * @author mingyan.su
 * @date   2023/3/25 13:47
 * @desc   用户管理类
 */
object UserManager {

    private var mmkv = MMKV.defaultMMKV()
    private val userLiveData = MutableLiveData<User?>()

    /**
     * 是否登录
     * @return Boolean
     */
    fun isLogin(): Boolean {
        return getUserInfo() != null
    }

    /**
     * 保存用户信息
     * @param user
     */
    fun saveUserInfo(user: User?) {
        mmkv.encode(KeyConstant.USER_INFO_DATA, user)
        if (userLiveData.hasObservers()) {
            userLiveData.postValue(user)
        }
    }

    /**
     * 保存用户手机号码
     * @param phone
     */
    fun saveUserPhone(phone: String?) {
        mmkv.encode(KeyConstant.KEY_USER_PHONE, phone)
    }

    /**
     * 保存用户本地头像
     * @param path
     */
    fun saveUserIcon(path: String) {
        val user = getUserInfo()
        user?.icon = path
        saveUserInfo(user)
    }


    /**
     * 获取用户信息
     * @return User
     */
    fun getUserInfo(): User? {
        return mmkv.decodeParcelable(KeyConstant.USER_INFO_DATA, User::class.java)
    }

    /**
     * 保存用户手机号码
     * @return phone
     */
    fun getUserPhone(): String? {
        return mmkv.decodeString(KeyConstant.KEY_USER_PHONE, "")
    }

    /**
     * 清除用户信息
     */
    fun clearUserInfo() {
        mmkv.encode(KeyConstant.USER_INFO_DATA, "")
        saveUserPhone("")
        if (userLiveData.hasObservers()) {
            userLiveData.postValue(null)
        }
    }

    /**
     * 获取用户LiveData
     * @return LiveData<User>
     */
    fun getUserLiveData(): LiveData<User?> {
        return userLiveData
    }
}