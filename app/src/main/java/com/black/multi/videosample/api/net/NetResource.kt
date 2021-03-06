package com.black.multi.videosample.api.net

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.black.multi.videosample.api.FetchData
import com.black.multi.videosample.api.IService
import com.black.multi.videosample.api.ServiceResponse
import com.black.xcommon.utils.EzLog

import kotlinx.coroutines.Dispatchers
import retrofit2.Response

/**
 * Created by wei.
 * Date: 2020/5/23 下午12:38
 * Description: 请求类
 */
abstract class NetResource<T> {

    open fun fetchData(): LiveData<Resource<T>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val api = FetchData.getService()
            try {
                //json 与 javabean 的格式不对应，到这里是会报解析错误的，如果不捕获，app 直接崩溃
                val response = requestNetResource(api)
                if (response!!.isSuccessful) {
//                    val data = response.body()?.data
                    val serviceResponse = response.body()
                    var data = serviceResponse?.data
                    if (ExceptionHandle.isConstraintError(serviceResponse!!.errorCode)) {
                        val handleException = ExceptionHandle.handleException(serviceResponse.errorCode, serviceResponse.errorMsg!!)
                        EzLog.d("${serviceResponse.errorMsg}")
                        emit(Resource.error(handleException, null))
                    } else {
                        emit(Resource.success(data))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    EzLog.d("errorBody--->${errorBody}")
                    val code = response.code()
                    val message = response.message()
                    emit(Resource.error(ExceptionHandle.handleException(code, message), null))
                }
            } catch (e: Exception) {
                EzLog.d("e--->${e.message}--->${e.javaClass}")
                emit(Resource.error(ExceptionHandle.handleException(e), null))
            }
        }
    }

    abstract suspend fun requestNetResource(api: IService): Response<ServiceResponse<T>>?

}