package com.example.mod_login.viewmodel

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

    fun getData() {
        request(requestCall = {ApiManager.api.getHomeList(1,10)},object: IHttpCallBack<ArticleList>{
//            override fun onSuccess(obj: ArticleList?) {
//                Logger.e(obj?.toString())
//            }

            override fun onFailure(obj: Any?) {
                Logger.e(obj?.toString())
            }

            override fun onSuccess(result: ArticleList) {
                Logger.e(result?.toString())
            }
        })
    }
}