package com.example.mod_home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lib_net.manager.ApiManager
import com.example.mod_home.repostory.HomeRepostory
import com.orhanobut.logger.Logger
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.bean.HotBean
import com.xwl.common_lib.callback.IHttpCallBack
import com.xwl.common_lib.dialog.TipsToast

/**
 * @author  lxw
 * @date 2023/9/12
 * descripe
 */
class HomeViewModel : BaseViewModel() {
    private val homeRepostory by lazy { HomeRepostory() }

    fun register(map: Map<String, Any>): LiveData<String> {
        val liveData: MutableLiveData<String> = MutableLiveData<String>()

        //方法一
        request(requestCall = { ApiManager.api.register(map) }, object : IHttpCallBack<Any> {
            override fun onSuccess(result: Any) {
                Logger.e(result.toString())
                liveData.value = "注册成功"
            }

            override fun onFailure(obj: Any?) {
                liveData.value = obj.toString()
            }
        })


        //方法二
//        launchUI(errorBlock = {code, msg ->
//            liveData.value = msg
//        }) {
//            val data = homeRepostory.register(map, loadingDialog = {
//                loadingChange.showDialog.value = it
//            })
//            Logger.e(data.toString())
//            liveData.value = "注册成功"
//        }
//
//        return registerLiveData

        return liveData
    }


    /**
     * 获取热门列表
     */
    fun getHotList(page: Int, size: Int): MutableLiveData<ArrayList<HotBean>?> {
        val hotLiveData: MutableLiveData<ArrayList<HotBean>?> =
            MutableLiveData<ArrayList<HotBean>?>()

        request(
            requestCall = { ApiManager.api.getHotList(page, size) },
            object : IHttpCallBack<ArrayList<HotBean>> {
                override fun onSuccess(result: ArrayList<HotBean>) {
                    hotLiveData.value = result
                }

                override fun onFailure(obj: Any?) {
                    hotLiveData.value = null
                    TipsToast.showTips(obj.toString())
                }
            })

        return hotLiveData
    }
}