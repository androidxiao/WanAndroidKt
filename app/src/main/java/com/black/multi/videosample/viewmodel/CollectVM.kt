package com.black.multi.videosample.viewmodel

import androidx.lifecycle.LiveData
import com.black.multi.videosample.api.IService
import com.black.multi.videosample.api.ServiceResponse
import com.black.multi.videosample.api.net.NetResource
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.model.CollectChapterModel
import retrofit2.Response

/**
 * Created by wei.
 * Date: 2020/6/2 14:09
 * Desc:
 */
class CollectVM : BaseViewModel<CollectChapterModel>() {

    companion object {
        val instance: CollectVM by lazy { CollectVM() }
    }

    fun collectChapter(id: Int) = object : NetResource<Any>() {
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<Any>>? {
            return api.collectInnerChapter(id)
        }

    }.fetchData()

    fun unCollectChapter(id: Int,originId:Int) = object : NetResource<Any>() {
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<Any>>? {
            return api.unCollectInnerChapter(id,originId)
        }

    }.fetchData()

    override fun getModels(): LiveData<Resource<CollectChapterModel>> =
        object : NetResource<CollectChapterModel>() {
            override suspend fun requestNetResource(api: IService): Response<ServiceResponse<CollectChapterModel>>? {
                return api.collectChapterList(page)
            }
        }.fetchData()
}