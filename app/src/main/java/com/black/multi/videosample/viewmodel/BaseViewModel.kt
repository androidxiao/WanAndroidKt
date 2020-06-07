package com.black.multi.videosample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.black.multi.videosample.api.net.Resource

/**
 * Created by wei.
 * Date: 2020/6/5 下午10:34
 * Description:
 */
abstract class BaseViewModel<T> :ViewModel() {

    var page = 0

    abstract fun getModels(): LiveData<Resource<T>>
}