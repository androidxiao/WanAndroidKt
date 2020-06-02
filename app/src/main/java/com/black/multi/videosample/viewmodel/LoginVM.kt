package com.black.multi.videosample.viewmodel

import com.black.multi.videosample.api.IService
import com.black.multi.videosample.api.ServiceResponse
import com.black.multi.videosample.api.net.NetResource
import com.black.multi.videosample.model.LoginModel
import retrofit2.Response

/**
 * Created by wei.
 * Date: 2020/6/2 9:51
 * Desc:
 */
class LoginVM {
    companion object{
        val instance : LoginVM by lazy { LoginVM() }
    }

    fun login(userName:String,password:String)=object : NetResource<LoginModel>(){
        override suspend fun requestNetResource(api: IService): Response<ServiceResponse<LoginModel>>? {
            return api.login(userName,password)
        }
    }.fetchData()
}