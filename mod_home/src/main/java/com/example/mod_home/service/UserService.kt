package com.example.mod_home.service

import android.content.Context
import androidx.lifecycle.LiveData
import com.alibaba.android.arouter.facade.annotation.Route
import com.xwl.common_lib.bean.User
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.manager.UserManager
import com.xwl.common_lib.service.IUserService

/**
 * @author mingyan.su
 * @date   2023/3/25 13:41
 * @desc   提供对IUserService接口的具体实现
 */
@Route(path = RoutMap.USER_SERVICE_USER)
class UserService : IUserService {
    /**
     * 是否登录
     * @return Boolean
     */
    override fun isLogin(): Boolean {
        return UserManager.isLogin()
    }

    /**
     * 获取用户信息
     * @return User or null
     */
    override fun getUserInfo(): User? {
        return UserManager.getUserInfo()
    }

    /**
     * 保存用户信息
     * @param user
     */
    override fun saveUserInfo(user: User?) {
        UserManager.saveUserInfo(user)
    }

    /**
     * 清除用户信息
     */
    override fun clearUserInfo() {
        UserManager.clearUserInfo()
    }

    /**
     * 获取User信息LiveData
     */
    override fun getUserLiveData(): LiveData<User?> {
        return UserManager.getUserLiveData()
    }

    /**
     * 保存用户手机号码
     * @param phone
     */
    override fun saveUserPhone(phone: String?) {
        UserManager.saveUserPhone(phone)
    }

    /**
     * 获取用户手机号码
     * @return phone
     */
    override fun getUserPhone(): String? {
        return UserManager.getUserPhone()
    }

    override fun init(context: Context?) {

    }
}