package com.example.lib_net.api

import com.xwl.common_base.response.BaseResponse
import com.xwl.common_lib.bean.BannerBean
import com.xwl.common_lib.bean.CourseListBean
import com.xwl.common_lib.bean.HotBean
import com.xwl.common_lib.constants.UrlConstants
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
interface ApiService {
    /**
     * 用户注册
     */
    @POST(UrlConstants.URL_REGISTER)
    suspend fun register(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): BaseResponse<Any>?

    /**
     * 获取热门列表
     */
    @GET(UrlConstants.BANNER_LIST_URL)
    suspend fun getBannerList(): BaseResponse<ArrayList<BannerBean>>?

    /**
     * 获取热门列表
     */
    @GET(UrlConstants.HOT_LIST_URL)
    suspend fun getHotList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<ArrayList<HotBean>>?

    /**
     * 获取课程列表
     */
    @GET(UrlConstants.COURSE_LIST_URL)
    suspend fun getCourseList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: Int
    ): BaseResponse<ArrayList<CourseListBean>>?

}