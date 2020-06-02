package com.black.multi.videosample.utils

/**
 * Created by wei.
 * Date: 2020/6/2 13:36
 * Desc:
 */
object User : SharedPreferencesUtils.Delegates() {

    override fun getSharedPreferencesName(): String = javaClass.simpleName

    var isLogin by boolean()
    var name by string()
}