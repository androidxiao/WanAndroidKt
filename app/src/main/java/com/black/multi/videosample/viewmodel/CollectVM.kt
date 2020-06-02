package com.black.multi.videosample.viewmodel

import androidx.lifecycle.ViewModel
import com.black.multi.videosample.api.IService
import com.black.multi.videosample.api.ServiceResponse
import com.black.multi.videosample.api.net.NetResource
import com.black.multi.videosample.model.CollectChapterModel
import retrofit2.Response

/**
 * Created by wei.
 * Date: 2020/6/2 14:09
 * Desc:
 */
class CollectVM : ViewModel() {

    companion object {
        val instance: CollectVM by lazy { CollectVM() }
    }

    fun collectChapter(page: Int) = object : NetResource<CollectChapterModel>() {
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<CollectChapterModel>>? {
            return api.collectChapter(page)
        }
    }.fetchData()

}