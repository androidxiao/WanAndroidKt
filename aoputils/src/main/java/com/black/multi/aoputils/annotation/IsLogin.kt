package com.black.multi.aoputils.annotation

/**
 * Created by wei.
 * Date: 2020/6/6 下午9:57
 * Description: 统一处理是否登录
 *
 * <p>
 *     loginType：0 -> 去登录
 *     loginType：-1 -> 退出登录
 * </p>
 */
const val Login_Out = -1

@Target(AnnotationTarget.FUNCTION,AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsLogin(val loginType:Int = 0)