package com.example.lib_net.api

import com.xwl.common_base.response.BaseResponse
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
//    /**
//     * 首页资讯
//     * @param page    页码
//     * @param pageSize 每页数量
//     */
//    @GET(UrlConstants.ARTICLE_LIST)
//    suspend fun getHomeList(
//        @Path("page") page: Int,
//        @Query("page_size") pageSize: Int
//    ): BaseResponse<ArticleList>?

    /**
     * 用户注册
     */
    @POST(UrlConstants.URL_REGISTER)
    suspend fun register(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): BaseResponse<Any>?

    /**
     * 获取热门列表
     */
    @GET(UrlConstants.HOT_URL)
    suspend fun getHotList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<ArrayList<HotBean>>?

}