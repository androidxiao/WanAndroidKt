package com.black.multi.videosample.viewmodel

import androidx.lifecycle.LiveData
import com.black.multi.videosample.api.IService
import com.black.multi.videosample.api.ServiceResponse
import com.black.multi.videosample.api.net.NetResource
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.model.RankScoreModel
import retrofit2.Response

/**
 * Created by wei.
 * Date: 2020/6/2 14:09
 * Desc:
 */
class ScoreRankVM : BaseListViewModel<RankScoreModel>() {

    companion object {
        val instance: ScoreRankVM by lazy { ScoreRankVM() }
    }

    override fun getModels(): LiveData<Resource<RankScoreModel>> =
        object : NetResource<RankScoreModel>() {
            override suspend fun requestNetResource(api: IService): Response<ServiceResponse<RankScoreModel>>? {
                return api.scoreRank(page+1)
            }
        }.fetchData()
}