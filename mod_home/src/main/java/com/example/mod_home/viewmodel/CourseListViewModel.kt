package com.example.mod_home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.lib_net.manager.ApiManager
import com.orhanobut.logger.Logger
import com.xwl.common_base.viewmodel.BaseViewModel
import com.xwl.common_lib.bean.CourseListBean
import com.xwl.common_lib.callback.IHttpCallBack
import com.xwl.common_lib.dialog.TipsToast

/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
class CourseListViewModel : BaseViewModel() {

    /**
     * 获取热门列表
     */
    fun getCourseList(
        page: Int,
        size: Int,
        type: Int,
        showloading: Boolean
    ): MutableLiveData<ArrayList<CourseListBean>?> {
        Logger.e("---->${type}")
        val courseListLiveData: MutableLiveData<ArrayList<CourseListBean>?> =
            MutableLiveData<ArrayList<CourseListBean>?>()

        request(
            requestCall = { ApiManager.api.getCourseList(page, size, type) },
            object : IHttpCallBack<ArrayList<CourseListBean>?> {
                override fun onSuccess(result: ArrayList<CourseListBean>?) {
                    courseListLiveData.value = result
                }

                override fun onFailure(obj: Any?) {
                    courseListLiveData.value = null
                    TipsToast.showTips(obj.toString())
                }
            }, showloading
        )

        return courseListLiveData
    }
}