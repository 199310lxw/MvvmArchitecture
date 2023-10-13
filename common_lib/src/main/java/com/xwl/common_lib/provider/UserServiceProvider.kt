package com.xwl.common_lib.provider

import androidx.lifecycle.LiveData
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.xwl.common_lib.bean.User
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.service.IUserService

/**
 * @author mingyan.su
 * @date   2023/3/26 07:09
 * @desc   UserService提供类，对外提供相关能力
 * 任意模块就能通过UserServiceProvider使用对外暴露的能力
 */
object UserServiceProvider {
    //// [The inject fields CAN NOT BE 'private'!!! please check field [userService] in class [UserServiceProvider]]
    @Autowired(name = RoutMap.USER_SERVICE_USER)
    lateinit var userService: IUserService

    init {
        ARouter.getInstance().inject(this)
    }

    /**
     * 是否登录
     * @return Boolean
     */
    fun isLogin(): Boolean {
        return userService.isLogin()
    }

    /**
     * 获取用户信息
     * @return User or null
     */
    fun getUserInfo(): User? {
        return userService.getUserInfo()
    }

    /**
     * 保存用户信息
     * @param user
     */
    fun saveUserInfo(user: User?) {
        userService.saveUserInfo(user)
    }

    /**
     * 清除用户信息
     */
    fun clearUserInfo() {
        userService.clearUserInfo()
    }

    /**
     * 获取User信息LiveData
     */
    fun getUserLiveData(): LiveData<User?> {
        return userService.getUserLiveData()
    }

    /**
     * 保存用户手机号码
     * @param phone
     */
    fun saveUserPhone(phone: String?) {
        userService.saveUserPhone(phone)
    }

    /**
     * 保存用户手机号码
     * @return phone
     */
    fun getUserPhone(): String? {
        return userService.getUserPhone()
    }

}