package com.black.multi.videosample.viewmodel

import androidx.lifecycle.ViewModel
import com.black.multi.videosample.api.IService
import com.black.multi.videosample.api.ServiceResponse
import com.black.multi.videosample.model.Banner
import com.black.multi.videosample.net.NetResource
import retrofit2.Response

/**
 * Created by wei.
 * Date: 2020/5/25 15:57
 * Desc:
 */
class HomeVm : ViewModel(){

    fun loadData()=object : NetResource<List<Banner>>(){
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<List<Banner>>>? {
            return api.getBanner()
        }
    }.fetchData()
}