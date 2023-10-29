package com.example.lib_net.api

import com.xwl.common_base.response.BaseResponse
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
        @Field("signature") signature: String,
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
     * 更新用户头像
     */
    @FormUrlEncoded
    @POST(UrlConstants.UPLOAD_FAVORITE_URL)
    suspend fun uploadFavorite(
        @Field("phone") phone: String,
        @Field("type") type: String,
        @Field("data") data: String,
    ): BaseResponse<String>?

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