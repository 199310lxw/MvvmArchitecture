package com.example.lib_net.api

import com.xwl.common_base.response.BaseResponse
import com.xwl.common_lib.constants.UrlConstants
import retrofit2.http.*

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
    @POST(UrlConstants.USER_REGISTER)
    suspend fun register(
        @QueryMap map: Map<String, @JvmSuppressWildcards Any>
    ): BaseResponse<Any>?

}