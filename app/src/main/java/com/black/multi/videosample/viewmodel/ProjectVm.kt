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
class ProjectVm : ViewModel(){

    companion object{
        val instance : ProjectVm by lazy { ProjectVm() }
    }

    fun getProjectTitle()=object : NetResource<List<ProjectTitleModel>>(){
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<List<ProjectTitleModel>>>? {
            return api.getProjectTitle()
        }
    }.fetchData()


}