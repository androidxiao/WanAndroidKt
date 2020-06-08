package com.black.multi.aoputils.utils

import com.black.xcommon.utils.SharedPreferencesUtils

/**
 * Created by wei.
 * Date: 2020/6/2 13:36
 * Desc: 用 sp 保存用户信息
 */
object User : SharedPreferencesUtils.Delegates() {

    override fun getSharedPreferencesName(): String = javaClass.simpleName

    var isLogin by boolean()
    var user by string()
    var isNightModel by boolean()
    var userScore by string()
}