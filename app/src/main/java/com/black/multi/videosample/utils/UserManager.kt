package com.black.multi.videosample.utils

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

    fun saveLogin(){
        User.isLogin = true
    }

    fun clear(){
        User.clearAll()
    }

    fun isLogin():Boolean{
        return  User.isLogin
    }
}