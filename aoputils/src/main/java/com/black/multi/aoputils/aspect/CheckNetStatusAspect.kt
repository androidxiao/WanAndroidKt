package com.black.multi.aoputils.aspect

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.black.multi.aoputils.R
import com.black.multi.aoputils.annotation.CheckNetStatus
import com.black.xcommon.utils.EzLog
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/**
 * Created by wei.
 * Date: 2020/6/7 下午5:31
 * Description:
 */
const val POINT_CUT_CHECK_NET_STATUS =
        "execution(@com.black.multi.aoputils.annotation.CheckNetStatus * *(..))"

@Aspect
class CheckNetStatusAspect {

    @Pointcut(POINT_CUT_CHECK_NET_STATUS)
    fun pointCutCheckNetStatus() {
    }

    @Around("pointCutCheckNetStatus()")
    @Throws(Throwable::class)
    fun checkNetStatus(joinPoint: ProceedingJoinPoint): Any? {
        return try {
            val signature = joinPoint.signature
            if (signature is MethodSignature) {
                val method =
                    signature.method
                val checkNetStatusHashPresent = method.isAnnotationPresent(
                    CheckNetStatus::class.java
                )
                if (checkNetStatusHashPresent) {
                    val checkNetStatus: CheckNetStatus =
                        method.getAnnotation(CheckNetStatus::class.java)
                    val msg: String = checkNetStatus.msg
                    val obj = joinPoint.getThis()
                    val context = getContext(obj)
                    if (context != null) {
                        if (!isNetAvailable(context)) {
                            if (TextUtils.isEmpty(msg)) {
                                Toast.makeText(
                                    context,
                                    context.resources.getString(R.string.no_net),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            }
                            return null
                        }
                    }
                }
            }
            joinPoint.proceed()
        } catch (e: Exception) {
            EzLog.d("CheckNetStatusAspect-exception--->${e.message}")
            joinPoint.proceed()
        }
    }

    private fun getContext(obj: Any): Context? {
        return when (obj) {
            is Activity -> {
                obj
            }
            is Fragment -> {
                obj.activity
            }
            is View -> {
                obj.context
            }
            else -> null
        }
    }

    private fun isNetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            var info: NetworkInfo?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networks =
                    connectivityManager.allNetworks
                if (networks != null && networks.isNotEmpty()) {
                    for (network in networks) {
                        info = connectivityManager.getNetworkInfo(network)
                        if (info != null && info.state == NetworkInfo.State.CONNECTED) {
                            return true
                        }
                    }
                }
                return false
            } else {
                info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                if (info != null && info.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
                info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                if (info != null && info.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
                info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX)
                if (info != null && info.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }
}