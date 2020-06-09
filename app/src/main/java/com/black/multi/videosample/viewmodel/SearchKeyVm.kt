package com.black.multi.videosample.viewmodel

import androidx.lifecycle.LiveData
import com.black.multi.videosample.api.IService
import com.black.multi.videosample.api.ServiceResponse
import com.black.multi.videosample.api.net.NetResource
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.model.HotSearchModel
import retrofit2.Response

/**
 * Created by wei.
 * Date: 2020/5/25 15:57
 * Desc:
 */
class SearchKeyVm : BaseListViewModel<List<HotSearchModel>>() {

    companion object{
        val INSTANCE : SearchKeyVm by lazy { SearchKeyVm() }
    }

    override fun getModels(): LiveData<Resource<List<HotSearchModel>>> =object :NetResource<List<HotSearchModel>>(){
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<List<HotSearchModel>>>? {
            return api.hotSearchKey()
        }
    }.fetchData()
}