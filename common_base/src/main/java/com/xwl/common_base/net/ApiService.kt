package com.xwl.common_lib.net

import com.xwl.common_base.bean.ArticleList
import com.xwl.common_base.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
interface ApiService {
    /**
     * 首页资讯
     * @param page    页码
     * @param pageSize 每页数量
     */
    @GET("/article/list/{page}/json")
    suspend fun getHomeList(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int
    ): BaseResponse<ArticleList>?
}