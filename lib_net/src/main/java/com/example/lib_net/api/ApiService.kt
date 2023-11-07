package com.example.lib_net.api

import com.example.lib_net.response.BaseResponse
import com.xwl.common_lib.bean.*
import com.xwl.common_lib.constants.UrlConstants
import okhttp3.MultipartBody
import retrofit2.http.*

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
     * 用户登陆
     */
    @FormUrlEncoded
    @POST(UrlConstants.LOGIN_URL)
    suspend fun login(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): BaseResponse<User>?

    /**
     * 更新用户信息
     */
    @FormUrlEncoded
    @POST(UrlConstants.SAVE_USER_INFO_URL)
    suspend fun updateUserInfo(
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("sex") sex: String,
        @Field("nickname") nickname: String,
        @Field("birthday") birthday: String,
        @Field("icon") icon: String,
        @Field("signature") signature: String
    ): BaseResponse<User>?

    /**
     * 更新用户头像
     */
    @Multipart
    @POST(UrlConstants.UPDATE_USER_ICON_URL)
    suspend fun updateUserIcon(
        @Part file: MultipartBody.Part
    ): BaseResponse<String>?

    /**
     * 收藏内容
     */
    @FormUrlEncoded
    @POST(UrlConstants.UPLOAD_FAVORITE_URL)
    suspend fun uploadFavorite(
        @Field("phone") phone: String,
        @Field("type") type: String,
        @Field("title") title: String,
        @Field("videoType") videoType: String,
        @Field("videoUrl") videoUrl: String,
        @Field("posterUrl") posterUrl: String
    ): BaseResponse<String>?

    /**
     * 取消收藏内容
     */
    @FormUrlEncoded
    @POST(UrlConstants.DISCOLLECT_FAVORITE_URL)
    suspend fun disCollectFavorite(
        @Field("phone") phone: String,
        @Field("videoUrl") videoUrl: String
    ): BaseResponse<String>?

    /**
     * 检查是否收藏了内容
     */
    @FormUrlEncoded
    @POST(UrlConstants.CHECK_COLLECTED_URL)
    suspend fun checkCollected(
        @Field("phone") phone: String,
        @Field("videoUrl") videoUrl: String
    ): BaseResponse<String>?


    /**
     * 获取收藏列表
     */
    @GET(UrlConstants.COLLECT_LIST_URL)
    suspend fun getCollectedList(
        @Query("phone") phone: String,
        @Query("type") type: String
    ): BaseResponse<ArrayList<CollectionBean>>?

    /**
     * 获取热门列表
     */
    @GET(UrlConstants.BANNER_LIST_URL)
    suspend fun getBannerList(): BaseResponse<ArrayList<BannerBean>>?

    /**
     * 获取分类列表
     */
    @GET(UrlConstants.SORT_LIST_URL)
    suspend fun getSortList(): BaseResponse<ArrayList<SortBean>>?

    /**
     * 获取热门列表
     */
    @GET(UrlConstants.VIDEO_LIST_URL)
    suspend fun getHotList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<ArrayList<VideoBean>>?
    
}