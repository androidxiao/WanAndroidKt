package com.black.multi.aoputils.aspect

import android.app.Activity
import androidx.fragment.app.Fragment
import com.black.xcommon.utils.EzLog
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect


/**
 * Created by wei.
 * Date: 2020/6/7 下午2:43
 * Description: 打印当前页面位置
 */

const val POINT_CUT_ACTIVITY_CREATE =
    "execution(* com.black.multi.videosample.base.ui.BaseActivity.beforeInitView(..))"

const val POINT_CUT_FRAGMENT_CREATE_VIEW = "execution(* com.black.multi.videosample.base.ui.BaseFragment.onCreateView(..))"

@Aspect
class ActivityLifeCycleAspect {

    @After(POINT_CUT_ACTIVITY_CREATE)
    open fun afterActivityCreate(joinPoint: JoinPoint?) {
        if (joinPoint != null) {
            val activity = joinPoint.getThis() as Activity
           EzLog.d("当前 Activity:" + activity.javaClass)
        }
    }

    @After(POINT_CUT_FRAGMENT_CREATE_VIEW)
    open fun afterFragmentCreateView(joinPoint: JoinPoint?) {
        if (joinPoint != null) {
            val fragment = joinPoint.getThis() as Fragment
           EzLog.d("当前 Fragment:" + fragment.javaClass)
        }
    }
}