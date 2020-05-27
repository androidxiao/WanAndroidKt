package com.black.multi.videosample.api

import com.black.multi.videosample.model.Banner
import com.black.multi.videosample.model.HomeModel
import com.black.multi.videosample.model.KnowledgeModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


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
}