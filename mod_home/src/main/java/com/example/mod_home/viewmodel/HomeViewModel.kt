package com.example.mod_home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.lib_net.manager.ApiManager
import com.example.mod_home.repostory.HomeRepostory
import com.orhanobut.logger.Logger
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.bean.BannerBean
import com.xwl.common_lib.bean.SortBean
import com.xwl.common_lib.bean.VideoBean
import com.xwl.common_lib.callback.IHttpCallBack

/**
 * @author  lxw
 * @date 2023/9/12
 * descripe
 */
class HomeViewModel : BaseViewModel() {
    private val homeRepostory by lazy { HomeRepostory() }

//    fun register(map: Map<String, Any>): LiveData<String> {
//        val liveData: MutableLiveData<String> = MutableLiveData<String>()
//
//        //方法一
//        request(requestCall = { ApiManager.api.register(map) }, object : IHttpCallBack<Any> {
//            override fun onSuccess(result: Any) {
//                Logger.i(result.toString())
//                liveData.value = "注册成功"
//            }
//
//            override fun onFailure(obj: Any?) {
//                liveData.value = obj.toString()
//            }
//        })


    //方法二
//        launchUI(errorBlock = {code, msg ->
//            liveData.value = msg
//        }) {
//            val data = homeRepostory.register(map, loadingDialog = {
//                loadingChange.showDialog.value = it
//            })
//            Logger.i(data.toString())
//            liveData.value = "注册成功"
//        }
//
//        return registerLiveData

//    return liveData
//}

    fun getBannerList(showloading: Boolean): MutableLiveData<ArrayList<BannerBean>?> {

        val bannerLiveData: MutableLiveData<ArrayList<BannerBean>?> =
            MutableLiveData<ArrayList<BannerBean>?>()
        request(
            requestCall = { ApiManager.api.getBannerList() },
            object : IHttpCallBack<ArrayList<BannerBean>?> {
                override fun onSuccess(result: ArrayList<BannerBean>?) {
                    bannerLiveData.value = result
                }

                override fun onFailure(obj: Any?) {

                }
            }, showloading
        )
        return bannerLiveData
    }

    fun getSortList(showloading: Boolean): MutableLiveData<ArrayList<SortBean>?> {
        val sortLiveData: MutableLiveData<ArrayList<SortBean>?> =
            MutableLiveData<ArrayList<SortBean>?>()
        request(
            requestCall = { ApiManager.api.getSortList() },
            object : IHttpCallBack<ArrayList<SortBean>?> {
                override fun onSuccess(result: ArrayList<SortBean>?) {
                    sortLiveData.value = result
                }

                override fun onFailure(obj: Any?) {

                }
            }, showloading
        )
        return sortLiveData
    }

    /**
     * 获取视频列表
     */
    fun getVideoList(
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
                    Logger.i("-------->${obj.toString()}")
                }
            }, showloading
        )

        return hotLiveData
    }
}