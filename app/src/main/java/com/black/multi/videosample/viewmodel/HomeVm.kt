package com.black.multi.videosample.viewmodel

import androidx.lifecycle.LiveData
import com.black.multi.videosample.api.IService
import com.black.multi.videosample.api.ServiceResponse
import com.black.multi.videosample.api.net.NetResource
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.model.Banner
import com.black.multi.videosample.model.HomeModel
import retrofit2.Response

/**
 * Created by wei.
 * Date: 2020/5/25 15:57
 * Desc:
 */
class HomeVm : BaseListViewModel<HomeModel>() {

    companion object{
        val instance : HomeVm by lazy { HomeVm() }
    }

    fun getBanner()=object : NetResource<List<Banner>>(){
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<List<Banner>>>? {
            return api.getBanner()
        }
    }.fetchData()

    override fun getModels(): LiveData<Resource<HomeModel>> =object :NetResource<HomeModel>(){
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<HomeModel>>? {
            return api.getHomeData(page)
        }
    }.fetchData()
}