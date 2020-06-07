package com.black.multi.aoputils.annotation

/**
 * Created by wei.
 * Date: 2020/6/7 下午5:30
 * Description: 检测当前网络是否可用
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CheckNetStatus(val msg:String = "暂无网络，请稍后重试")