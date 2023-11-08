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
            requestCall = { ApiManager.api.getVideoList(page, size) },
            object : IHttpCallBack<ArrayList<VideoBean>?> {
                override fun onSuccess(result: ArrayList<VideoBean>?) {
                    hotLiveData.value = result
                }

                override fun onFailure(obj: Any?) {

                }
            }, showloading
        )

        return hotLiveData
    }

    /**
     * 收藏视频
     */
    fun uploadFavoriteVideo(
        phone: String,
        type: String,
        title: String,
        videoType: String,
        videoUrl: String,
        posterUrl: String,
        showloading: Boolean
    ): MutableLiveData<String?> {
        val favoriteLiveData: MutableLiveData<String?> =
            MutableLiveData<String?>()

        request(
            requestCall = {
                ApiManager.api.uploadFavorite(
                    phone,
                    type,
                    title,
                    videoType,
                    videoUrl,
                    posterUrl
                )
            },
            object : IHttpCallBack<String?> {
                override fun onSuccess(result: String?) {
                    favoriteLiveData.value = result
                }

                override fun onFailure(obj: Any?) {

                }
            }, showloading
        )

        return favoriteLiveData
    }

    /**
     * 取消收藏视频
     */
    fun disCollectVideo(
        phone: String,
        videoUrl: String,
        showloading: Boolean
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
            }, showloading
        )

        return disCollectLiveData
    }

    /**
     * 检查视频是否收藏了
     */
    fun checkIsColleced(
        phone: String,
        videoUrl: String,
        showloading: Boolean = true
    ): MutableLiveData<String?> {
        val checkLiveData: MutableLiveData<String?> =
            MutableLiveData<String?>()

        request(
            requestCall = { ApiManager.api.checkCollected(phone, videoUrl) },
            object : IHttpCallBack<String?> {
                override fun onSuccess(result: String?) {
                    checkLiveData.value = result
                }

                override fun onFailure(obj: Any?) {
                   
                }
            }, showloading
        )

        return checkLiveData
    }

}