package com.example.mod_home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.lib_net.manager.ApiManager
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.bean.HotBean
import com.xwl.common_lib.callback.IHttpCallBack

/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
class CourseDetailViewModel : BaseViewModel() {

    /**
     * 获取热门列表
     */
    fun getRecommendList(
        page: Int,
        size: Int,
        showloading: Boolean
    ): MutableLiveData<ArrayList<HotBean>?> {
        val hotLiveData: MutableLiveData<ArrayList<HotBean>?> =
            MutableLiveData<ArrayList<HotBean>?>()

        request(
            requestCall = { ApiManager.api.getHotList(page, size) },
            object : IHttpCallBack<ArrayList<HotBean>?> {
                override fun onSuccess(result: ArrayList<HotBean>?) {
                    hotLiveData.value = result
                }

                override fun onFailure(obj: Any?) {
                    error.value = obj.toString()
                }
            }, showloading
        )

        return hotLiveData
    }
}