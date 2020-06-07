package com.black.multi.aoputils.aspect

import android.os.SystemClock
import android.view.View
import com.black.multi.aoputils.annotation.Except
import com.black.multi.aoputils.annotation.IntervalTime
import com.black.xcommon.utils.EzLog
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/**
 * Created by wei.
 * Date: 2020/6/7 下午3:38
 * Description: 所有的点击事件去重
 */

private var sIntervalTime = 500L

/**
 * java 代码 setOnClickListener
 * 以及自定义的 DataBinding 点击事件（因为 DataBinding 最后调用的仍然是 setOnClickListener#onClick 方法）*/
private const val POINT_CUT_FILTER_CLICK_JAVA =
    "execution(* android.view.View.OnClickListener.onClick(..))"

/**
 * xml android:onClick（该匹配无效，尝试了修改各种路径都不行
 * 故单独写了一个[com.black.multi.aoputils.annotation.SingleClick]注解用于处理 xml 中的点击事件）
 */
private const val POINT_CUT_FILTER_CLICK_XML =
    "execution(* androidx.appcompat.app.AppCompatViewInflater.DeclaredOnClickListener.onClick(..))"

/**
 * 用来处理 xml 中的 onClick 事件
 */
private const val POINT_CUT_REPLACE_FILTER_CLICK_XML =
    "execution(@com.black.multi.aoputils.annotation.SingleClick * *(..))"

private const val TAG_SINGLE_CLICK = -1000001

@Aspect
class ClickAspect {

    @Pointcut(POINT_CUT_FILTER_CLICK_JAVA)
    open fun pointCutFilterClickJava() {
    }

    @Pointcut(POINT_CUT_FILTER_CLICK_XML)
    open fun pointCutFilterClickXML() {
    }

    @Pointcut(POINT_CUT_REPLACE_FILTER_CLICK_XML)
    open fun pointCutReplaceFilterClickXML() {
    }

    @Before(POINT_CUT_FILTER_CLICK_JAVA)
    @Throws(Throwable::class)
    open fun activityLifeCycleMethod(joinPoint: JoinPoint) {
        val methodSignature =
            joinPoint.signature as MethodSignature
        val className = methodSignature.declaringType.simpleName
        val methodName = methodSignature.name

        EzLog.d("className--->${className}--->methodName--->${methodName}")
    }

    @Around("pointCutFilterClickJava()||pointCutReplaceFilterClickXML()")
    @Throws(Throwable::class)
    open fun filterClick(joinPoint: ProceedingJoinPoint) {
        try {
            val signature = joinPoint.signature
            if (signature is MethodSignature) {
                var method =
                    signature.method
                if (method == null) {

                }
                if (method != null && method.isAnnotationPresent(Except::class.java)) {
                    //有 except 注解时，跳过验证，直接执行原有逻辑
                    joinPoint.proceed()
                    return
                }
                if (method != null && method.isAnnotationPresent(IntervalTime::class.java)) {
                    val intervalTime: IntervalTime = method.getAnnotation(IntervalTime::class.java)
                    sIntervalTime = intervalTime.time
                }
            }
            val args = joinPoint.args
            val view = getViewFromArgs(args)

            if (view == null) {
                //为空，执行原有逻辑
                joinPoint.proceed()
                return
            }
            val lastClickTime =
                view.getTag(TAG_SINGLE_CLICK) as Long?
            if (lastClickTime == null) {
                //第一次单击直接执行
                view.setTag(TAG_SINGLE_CLICK, SystemClock.elapsedRealtime())
                joinPoint.proceed()
                return
            }
            if (canClick(lastClickTime)) {
                //在合理地间隔外，可以执行
                view.setTag(TAG_SINGLE_CLICK, SystemClock.elapsedRealtime())
                joinPoint.proceed()
                return
            }
        } catch (ex: Exception) {
            EzLog.d("ClickAspect-Exception-->${ex.message}")
            //异常时，执行原有逻辑
            joinPoint.proceed()
        }
    }

    private fun getViewFromArgs(args: Array<Any>?): View? {
        if (args != null && args.isNotEmpty()) {
            val arg = args[0]
            if (arg is View) {
                return arg
            }
        }
        return null
    }

    private fun canClick(lastClickTime: Long): Boolean {
        return SystemClock.elapsedRealtime() - lastClickTime >= sIntervalTime
    }

}