package com.black.multi.videosample.api

import com.black.multi.videosample.model.*
import retrofit2.Response
import retrofit2.http.*


/**
 * Created by wei.
 * Date: 2020/5/23 下午12:55
 * Description:
 */
interface IService {

    //Banner
    @GET("banner/json")
    suspend fun getBanner(): Response<ServiceResponse<List<Banner>>>

    //首页文章列表
    @GET("article/list/{page}/json")
    suspend fun getHomeData(@Path("page") page: Int): Response<ServiceResponse<HomeModel>>

    //体系数据
    @GET("tree/json")
    suspend fun getKnowledge(): Response<ServiceResponse<List<KnowledgeModel>>>

    //体系下列表
    @GET("article/list/{page}/json")
    suspend fun getKnowledgeList(@Path("page") page: Int, @Query("cid") cid: Int): Response<ServiceResponse<KnowledgeListModel>>

    //项目标题
    @GET("project/tree/json")
    suspend fun getProjectTitle(): Response<ServiceResponse<List<ProjectTitleModel>>>

    //项目列表
    @GET("project/list/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int, @Query("cid") cid: Int): Response<ServiceResponse<ProjectListModel>>

    //导航数据
    @GET("navi/json")
    suspend fun getNavData(): Response<ServiceResponse<List<NavModel>>>

    //登录
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): Response<ServiceResponse<LoginModel>>

    //收藏文章列表
    @GET("lg/collect/list/{page}/json")
    suspend fun collectChapterList(@Path("page") page: Int): Response<ServiceResponse<CollectChapterModel>>

    //获取个人积分
    @GET("lg/coin/userinfo/json")
    suspend fun getPersonalScore(): Response<ServiceResponse<PersonalScoreModel>>

    //收藏站内文章
    @FormUrlEncoded
    @POST("lg/collect/{id}/json")
    suspend fun collectInnerChapter(@Path("id") id: Int): Response<ServiceResponse<Any>>

    //收藏站内文章
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    suspend fun unCollectInnerChapter(@Path("id") id: Int, @Field("originId") originId: Int): Response<ServiceResponse<Any>>

    //退出登录
    @GET("user/logout/json")
    suspend fun loginOut(): Response<ServiceResponse<Any>>

    //积分排行
    @GET("coin/rank/{page}/json")
    suspend fun scoreRank(@Path("page") page: Int): Response<ServiceResponse<RankScoreModel>>

    //热搜关键字
    @GET("hotkey/json")
    suspend fun hotSearchKey(): Response<ServiceResponse<List<HotSearchModel>>>

    //搜索列表
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun searchChapterList(@Path("page") page: Int, @Field("k") k: String): Response<ServiceResponse<HomeModel>>
}