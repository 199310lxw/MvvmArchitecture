package com.example.mod_login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.xwl.common_base.bean.ArticleList
import com.xwl.common_base.net.ApiManager
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
    init {
        registerLiveData = MutableLiveData<String>()
    }
    fun getData() {
        request(requestCall = {ApiManager.api.getHomeList(1,10)},object: IHttpCallBack<ArticleList>{
            override fun onFailure(obj: Any?) {
                Logger.e(obj?.toString())
            }

            override fun onSuccess(result: ArticleList) {
                Logger.e(result?.toString())
            }
        })
    }

    fun register(map: Map<String,Any>): LiveData<String?> {
        request(requestCall = {ApiManager.api.register(map)},object: IHttpCallBack<Any>{
            override fun onSuccess(result: Any) {
                Logger.e(result.toString())
//                registerLiveData.postValue("注册成功")
                registerLiveData.value = "注册成功"
            }

            override fun onFailure(obj: Any?) {
//                registerLiveData.postValue(obj.toString())
                registerLiveData.value = obj.toString()
            }
        })
        return registerLiveData
    }
}