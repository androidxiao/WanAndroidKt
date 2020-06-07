package com.black.multi.aoputils.annotation

/**
 * Created by wei.
 * Date: 2020/6/7 下午3:58
 * Description: 可以自定义连续点击控件的有效时间间隔（默认 500ms 内的点击事件只有第一次有效）
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class IntervalTime(val time:Long = 500L)