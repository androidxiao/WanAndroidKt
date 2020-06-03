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

    fun saveLoginUserInfo(info:String){
        User.isLogin = true
        User.user = info
    }

    fun clear(){
        User.clearAll()
    }

    fun isLogin():Boolean{
        return  User.isLogin
    }
}