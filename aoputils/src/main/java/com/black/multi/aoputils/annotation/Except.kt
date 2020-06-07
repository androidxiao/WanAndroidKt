package com.black.multi.aoputils.annotation

/**
 * Created by wei.
 * Date: 2020/6/7 下午3:59
 * Description: 可以跳过时间间隔验证
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Except