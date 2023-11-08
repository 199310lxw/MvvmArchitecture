package com.example.mod_home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.lib_net.manager.ApiManager
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.bean.CollectionBean
import com.xwl.common_lib.callback.IHttpCallBack

/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
class CollectedViewModel : BaseViewModel() {

    /**
     * 获取收藏列表
     */
    fun getCollectedList(
        phone: String,
        type: String,
        showLoading: Boolean
    ): MutableLiveData<ArrayList<CollectionBean>?> {
        val collectedLiveData: MutableLiveData<ArrayList<CollectionBean>?> =
            MutableLiveData<ArrayList<CollectionBean>?>()
        request(
            requestCall = { ApiManager.api.getCollectedList(phone, type) },
            object : IHttpCallBack<ArrayList<CollectionBean>?> {
                override fun onSuccess(result: ArrayList<CollectionBean>?) {
                    collectedLiveData.value = result
                }

                override fun onFailure(obj: Any?) {

                }
            }, showLoading
        )
        return collectedLiveData
    }

    fun disCollectVideo(
        phone: String,
        videoUrl: String,
        showLoading: Boolean
    ): MutableLiveData<String?> {
        val disCollectLiveData: MutableLiveData<String?> =
            MutableLiveData<String?>()
        request(
            requestCall = {
                ApiManager.api.disCollectFavorite(
                    phone,
                    videoUrl
                )
            },
            object : IHttpCallBack<String?> {
                override fun onSuccess(result: String?) {
                    disCollectLiveData.value = result
                }

                override fun onFailure(obj: Any?) {
                 
                }
            }, showLoading
        )

        return disCollectLiveData
    }
}