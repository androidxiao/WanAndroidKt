package com.black.multi.videosample.api

import com.black.multi.videosample.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


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
    suspend fun getHomeData(@Path("page") page:Int): Response<ServiceResponse<HomeModel>>

    //体系数据
    @GET("tree/json")
    suspend fun getKnowledge(): Response<ServiceResponse<List<KnowledgeModel>>>

    //体系下列表
    @GET("article/list/{page}/json")
    suspend fun getKnowledgeList(@Path("page") page:Int,@Query("cid") cid:Int): Response<ServiceResponse<KnowledgeListModel>>

    //项目标题
    @GET("project/tree/json")
    suspend fun getProjectTitle(): Response<ServiceResponse<List<ProjectTitleModel>>>

    //项目列表
    @GET("project/list/{page}/json")
    suspend fun getProjectList(@Path("page") page:Int,@Query("cid") cid:Int): Response<ServiceResponse<ProjectListModel>>

    //导航数据
    @GET("navi/json")
    suspend fun getNavData(): Response<ServiceResponse<List<NavModel>>>
}