package com.black.multi.videosample.utils

import android.content.Context
import android.widget.Toast
import com.black.multi.aoputils.utils.User
import com.black.multi.videosample.model.LoginModel
import com.black.xcommon.utils.GsonUtils

/**
 * Created by wei.
 * Date: 2020/6/2 10:03
 * Desc:
 */
fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toast(message: Int) =
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()

fun userName():String{
        val loginModel = GsonUtils.getGson().fromJson<LoginModel>(User.user, LoginModel::class.java)
        return loginModel.username

}