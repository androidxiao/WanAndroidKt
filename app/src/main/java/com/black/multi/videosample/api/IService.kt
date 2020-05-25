package com.black.multi.videosample.api

import com.black.multi.videosample.model.Banner
import retrofit2.Response
import retrofit2.http.GET


/**
 * Created by wei.
 * Date: 2020/5/23 下午12:55
 * Description:
 */
interface IService {

    @GET("banner/json")
    suspend fun getBanner(): Response<ServiceResponse<List<Banner>>>
}