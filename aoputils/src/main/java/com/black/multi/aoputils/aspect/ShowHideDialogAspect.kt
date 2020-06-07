package com.black.multi.aoputils.aspect

import android.app.ProgressDialog
import android.content.Context
import com.black.multi.aoputils.annotation.ShowDialog
import com.black.xcommon.utils.EzLog
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/**
 * Created by wei.
 * Date: 2020/6/7 下午6:37
 * Description:
 */
const val POINT_CUT_SHOW_DIALOG =
    "execution(@com.black.multi.aoputils.annotation.ShowDialog * *(..))"
const val POINT_CUT_HIDE_DIALOG =
    "execution(@com.black.multi.aoputils.annotation.HideDialog * *(..))"
@Aspect
class ShowHideDialogAspect {


    private var mDialog: ProgressDialog? = null

    @Pointcut(POINT_CUT_SHOW_DIALOG)
    fun pointCutShowDialog() {
    }

    @After(POINT_CUT_HIDE_DIALOG)
    fun afterHideDialog() {
        if (mDialog != null) {
            mDialog!!.cancel()
        }
    }

    @Around("pointCutShowDialog()")
    @Throws(Throwable::class)
    fun showHideDialog(joinPoint: ProceedingJoinPoint) {
        try {
            val methodSignature =
                joinPoint.signature as MethodSignature
            val showDialog =
                methodSignature.method.getAnnotation(ShowDialog::class.java)
            val hint = showDialog.hint
            if (showDialog != null) {
                if (mDialog == null) {
                    mDialog =
                        ProgressDialog(joinPoint.getThis() as Context)
                }
                if(hint.isNotEmpty()){
                    mDialog!!.setMessage(hint)
                }else {
                    mDialog!!.setMessage("Loading...")
                }
                mDialog!!.setCancelable(false)
                mDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                mDialog!!.show()
                joinPoint.proceed()
            }
        } catch (e: Exception) {
            EzLog.d("ex-->" + e.message)
            joinPoint.proceed()
        }
    }
}