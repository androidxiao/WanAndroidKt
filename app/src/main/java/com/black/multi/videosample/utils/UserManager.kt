package com.black.multi.videosample.utils

import kotlin.LazyThreadSafetyMode.*

/**
 * Created by wei.
 * Date: 2020/5/25 11:10
 * Desc:
 */
class UserManager private constructor(){
    companion object{
        val instance:UserManager by lazy {
            UserManager()
        }
    }

    fun isLogin():Boolean{
        return true
    }
}