package com.black.multi.videosample.api

import java.io.Serializable

/**
 * Created by wei.
 * Date: 2020/5/23 下午6:07
 * Description:
 */
open class ServiceResponse<T> : Serializable {
    open var data: T? = null
    open var errorCode = 0
    open var errorMsg: String? = null
}