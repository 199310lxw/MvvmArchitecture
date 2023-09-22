package com.example.mod_login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lib_net.manager.ApiManager
import com.example.mod_login.repository.LoginRepostory
import com.orhanobut.logger.Logger
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.callback.IHttpCallBack

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
class LoginViewModel: BaseViewModel() {
    var registerLiveData: MutableLiveData<String>
         private set
    private val loginRepostory by lazy { LoginRepostory() }

    init {
        registerLiveData = MutableLiveData<String>()
    }


    fun register(map: Map<String,Any>): LiveData<String?> {
        //方法一
        request(requestCall = { ApiManager.api.register(map)},object: IHttpCallBack<Any> {
            override fun onSuccess(result: Any) {
                Logger.e(result.toString())
                registerLiveData.value = "注册成功"
            }

            override fun onFailure(obj: Any?) {
                registerLiveData.value = obj.toString()
            }
        })

//        //方法二
//        launchUI(errorBlock = {code, msg ->
//           registerLiveData.value = msg
//        }) {
//           val data = loginRepostory.register(map, loadingDialog = {
//               loadingChange.showDialog.value = it
//           })
//            Logger.e(data.toString())
//            registerLiveData.value = "注册成功"
//        }

        return registerLiveData
    }
}