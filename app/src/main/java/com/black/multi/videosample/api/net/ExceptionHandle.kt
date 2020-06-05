package com.black.multi.videosample.api.net

import android.net.ParseException
import com.black.multi.videosample.api.net.ExceptionHandle.ERROR.HTTP_ERROR
import com.black.multi.videosample.api.net.ExceptionHandle.ERROR.LOGIN_FIRST
import com.black.multi.videosample.utils.Login_First
import com.google.gson.JsonParseException
import com.jeremyliao.liveeventbus.LiveEventBus
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

/**
 * Created by wei.
 * Date: 2020/5/23 下午4:59
 * Description:
 */
object ExceptionHandle {
    private const val UNAUTHORIZED = 401
    private const val FORBIDDEN = 403
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val BAD_GATEWAY = 502
    private const val SERVICE_UNAVAILABLE = 503
    private const val GATEWAY_TIMEOUT = 504

    fun handleException(code: Int, msg: String): String {
        var exceptionMsg: String
        when (code) {
            UNAUTHORIZED -> {
                exceptionMsg = "< UNAUTHORIZED > 未认证"
            }
            FORBIDDEN -> {
                exceptionMsg = "< FORBIDDEN > 权限禁止"
            }
            NOT_FOUND -> {
                exceptionMsg = "< NOT_FOUND > 未找到网页"
            }
            REQUEST_TIMEOUT -> {
                exceptionMsg = "< REQUEST_TIMEOUT > 请求超时"
            }
            GATEWAY_TIMEOUT -> {
                exceptionMsg = "< GATEWAY_TIMEOUT > 网关超时"
            }
            INTERNAL_SERVER_ERROR -> {
                exceptionMsg = "< INTERNAL_SERVER_ERROR > 服务器错误"
            }
            BAD_GATEWAY -> {
                exceptionMsg = "< BAD_GATEWAY > 网关错误"
            }
            SERVICE_UNAVAILABLE -> {
                exceptionMsg = "< SERVICE_UNAVAILABLE > 服务器不可用"
            }
            HTTP_ERROR ->{
                exceptionMsg = "< SERVICE_UNAVAILABLE > 协议出错"
            }
            else -> {
                if (msg.isNotEmpty()) {
                    return msg
                }
                return "< 未知错误 > "
            }
        }
        return exceptionMsg
    }

    fun handleException(e:Exception):String{
        return when(e){
            is JsonParseException-> "解析出错"
            is JSONException -> "解析出错"
            is ParseException -> "解析出错"
            is ConnectException -> "连接失败"
            is SSLHandshakeException -> "证书验证失败"
            is ConnectTimeoutException -> "连接超时"
            is SocketTimeoutException -> "连接超时"
            else -> "未知错误"
        }
    }

    object ERROR {
        /**
         * 协议出错
         */
        const val HTTP_ERROR = 1003
        /**
         * 请先登录
         */
        const val LOGIN_FIRST = -1001
    }

    /**
     * 是否是约定错误
     */
    fun isConstraintError(code:Int):Boolean{
        return when (code) {
            LOGIN_FIRST -> {
                LiveEventBus.get(Login_First).post(false)
                true
            }
            else -> false
        }
    }
}