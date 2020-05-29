package com.black.multi.videosample.viewmodel

import androidx.lifecycle.ViewModel
import com.black.multi.videosample.api.IService
import com.black.multi.videosample.api.ServiceResponse
import com.black.multi.videosample.api.net.NetResource
import com.black.multi.videosample.model.*
import retrofit2.Response

/**
 * Created by wei.
 * Date: 2020/5/25 15:57
 * Desc:
 */
class NavVm : ViewModel(){

    companion object{
        val instance : NavVm by lazy { NavVm() }
    }

    fun getNavData()=object : NetResource<List<NavModel>>(){
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<List<NavModel>>>? {
            return api.getNavData()
        }
    }.fetchData()

}