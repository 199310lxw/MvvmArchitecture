package com.example.mod_home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.lib_net.manager.ApiManager
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.bean.VideoBean
import com.xwl.common_lib.callback.IHttpCallBack

/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
class CourseDetailViewModel : BaseViewModel() {

    /**
     * 获取推荐列表
     */
    fun getRecommendList(
        page: Int,
        size: Int,
        showloading: Boolean
    ): MutableLiveData<ArrayList<VideoBean>?> {
        val hotLiveData: MutableLiveData<ArrayList<VideoBean>?> =
            MutableLiveData<ArrayList<VideoBean>?>()

        request(
            requestCall = { ApiManager.api.getHotList(page, size) },
            object : IHttpCallBack<ArrayList<VideoBean>?> {
                override fun onSuccess(result: ArrayList<VideoBean>?) {
                    hotLiveData.value = result
                }

                override fun onFailure(obj: Any?) {
                    error.value = obj.toString()
                }
            }, showloading
        )

        return hotLiveData
    }

    /**
     * 获取推荐列表
     */
    fun uploadFavoriteVideo(
        phone: String,
        type: String,
        data: String,
        showloading: Boolean
    ): MutableLiveData<String?> {
        val favoriteLiveData: MutableLiveData<String?> =
            MutableLiveData<String?>()

        request(
            requestCall = { ApiManager.api.uploadFavorite(phone, type, data) },
            object : IHttpCallBack<String?> {
                override fun onSuccess(result: String?) {
                    favoriteLiveData.value = result
                }

                override fun onFailure(obj: Any?) {
                    error.value = obj.toString()
                }
            }, showloading
        )

        return favoriteLiveData
    }

}