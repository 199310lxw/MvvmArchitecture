package com.example.mod_home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.bean.User

/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
class UserInfoViewModel : BaseViewModel() {

    /**
     * 获取热门列表
     */
    fun updateUserInfo(): MutableLiveData<User?> {
        val userListLiveData: MutableLiveData<User?> =
            MutableLiveData<User?>()
//
//        request(
//            requestCall = { ApiManager.api.updateUserInfo() },
//            object : IHttpCallBack<User> {
//                override fun onSuccess(result: User) {
//                    userListLiveData.value = result
//                }
//
//                override fun onFailure(obj: Any?) {
//
//                    TipsToast.showTips(obj.toString())
//                }
//            })

        return userListLiveData
    }
}