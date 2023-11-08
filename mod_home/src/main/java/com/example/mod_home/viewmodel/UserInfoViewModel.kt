package com.example.mod_home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.lib_net.manager.ApiManager
import com.orhanobut.logger.Logger
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.bean.User
import com.xwl.common_lib.callback.IHttpCallBack
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
class UserInfoViewModel : BaseViewModel() {


    /**
     * 上传用户头像
     */
    fun uploadUserIcon(file: File): MutableLiveData<String?> {
        val uploadListLiveData: MutableLiveData<String?> =
            MutableLiveData<String?>()
        val requestFile: RequestBody = getRequestBody(file)
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", file.name, requestFile)
        request(
            requestCall = { ApiManager.api.updateUserIcon(body) },
            object : IHttpCallBack<String?> {
                override fun onSuccess(result: String?) {
                    Logger.i(result)
                    uploadListLiveData.value = result
                }

                override fun onFailure(obj: Any?) {
                    Logger.i(obj.toString())

                }
            })

        return uploadListLiveData
    }

    /**
     * 上传用户信息
     */
    fun updateUserInfo(user: User): MutableLiveData<User?> {
        val userListLiveData: MutableLiveData<User?> =
            MutableLiveData<User?>()

        request(
            requestCall = {
                ApiManager.api.updateUserInfo(
                    user.phone,
                    user.password,
                    user.sex,
                    user.nickname,
                    user.birthday,
                    user.icon,
                    user.signature
                )
            },
            object : IHttpCallBack<User?> {
                override fun onSuccess(result: User?) {
                    userListLiveData.value = result
                }

                override fun onFailure(obj: Any?) {
                   
                }
            })

        return userListLiveData
    }
}