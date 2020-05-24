package com.black.multi.videosample.api


/**
 * Created by wei.
 * Date: 2020/5/23 下午12:55
 * Description:
 */
object FetchData {

    fun getService(): IService = ApiClient.createService(IService::class.java)
}