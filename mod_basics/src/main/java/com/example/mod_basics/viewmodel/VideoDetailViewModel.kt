package com.example.mod_basics.viewmodel

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
class VideoDetailViewModel : BaseViewModel() {

    /**
     * 获取热门列表
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
                    
                }
            }, showloading
        )

        return hotLiveData
    }
}