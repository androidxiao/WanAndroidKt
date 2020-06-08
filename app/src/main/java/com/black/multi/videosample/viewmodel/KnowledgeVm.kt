package com.black.multi.videosample.viewmodel

import com.black.multi.videosample.api.IService
import com.black.multi.videosample.api.ServiceResponse
import com.black.multi.videosample.api.net.NetResource
import com.black.multi.videosample.model.KnowledgeListModel
import com.black.multi.videosample.model.KnowledgeModel
import retrofit2.Response

/**
 * Created by wei.
 * Date: 2020/5/25 15:57
 * Desc:
 */
class KnowledgeVm : BaseViewModel<KnowledgeListModel>(){

    companion object{
        val instance : KnowledgeVm by lazy { KnowledgeVm() }
    }

    fun getKnowledge()=object : NetResource<List<KnowledgeModel>>(){
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<List<KnowledgeModel>>>? {
            return api.getKnowledge()
        }
    }.fetchData()

    var cid = 0

    override fun getModels()=object : NetResource<KnowledgeListModel>(){
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<KnowledgeListModel>>? {
            return api.getKnowledgeList(page,cid)
        }
    }.fetchData()

}