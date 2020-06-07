package com.black.multi.aoputils.annotation

/**
 * Created by wei.
 * Date: 2020/6/7 下午6:35
 * Description:
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ShowDialog(val hint:String = "正在加载，请稍后...")