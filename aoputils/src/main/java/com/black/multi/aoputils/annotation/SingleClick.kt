package com.black.multi.aoputils.annotation

/**
 * Created by wei.
 * Date: 2020/6/7 下午3:53
 * Description: 被标记的 xml 中的 onClick 方法在被点击时会过滤重复
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SingleClick