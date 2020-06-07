package com.black.multi.videosample.viewmodel

import androidx.lifecycle.LiveData
import com.black.multi.videosample.api.IService
import com.black.multi.videosample.api.ServiceResponse
import com.black.multi.videosample.api.net.NetResource
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.model.ProjectListModel
import com.black.multi.videosample.model.ProjectTitleModel
import retrofit2.Response

/**
 * Created by wei.
 * Date: 2020/5/25 15:57
 * Desc:
 */
class ProjectVm : BaseViewModel<ProjectListModel>(){

    companion object{
        val instance : ProjectVm by lazy { ProjectVm() }
    }

    fun getProjectTitle()=object : NetResource<List<ProjectTitleModel>>(){
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<List<ProjectTitleModel>>>? {
            return api.getProjectTitle()
        }
    }.fetchData()

//    fun getProjectList(page:Int,cid:Int)
    var cid:Int = 0

    open fun setcid(cid: Int){
        this.cid = cid
    }

    override fun getModels(): LiveData<Resource<ProjectListModel>> =object : NetResource<ProjectListModel>(){
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<ProjectListModel>>? {
            return api.getProjectList(page,cid)
        }
    }.fetchData()


}