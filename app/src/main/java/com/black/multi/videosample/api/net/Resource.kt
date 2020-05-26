package com.black.multi.videosample.api.net

/**
 * Created by wei.
 * Date: 2020/5/23 下午5:05
 * Description:
 */
data class Resource<out T>(val status: Status, val data: T?, val msg: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}


//TODO: This class shall replace LiveDataResponse going forward