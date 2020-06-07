package com.black.multi.aoputils.utils

/**
 * Created by wei.
 * Date: 2020/5/25 11:10
 * Desc: 用户信息管理类
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

    //声明 isLoginListener 是一个函数（单方法接口）,入参类型自拟，无返回值
    lateinit var isLoginListener:(loginType:Int)->Unit

    open fun setIsLoginListener(isLoginListener:(Int)->Unit){
        this.isLoginListener = isLoginListener
    }
}