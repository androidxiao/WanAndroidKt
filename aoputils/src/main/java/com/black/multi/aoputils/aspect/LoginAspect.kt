package com.black.multi.aoputils.aspect

import com.black.multi.aoputils.annotation.IsLogin
import com.black.multi.aoputils.annotation.Login_Out
import com.black.multi.aoputils.utils.UserManager
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/**
 * Created by wei.
 * Date: 2020/6/6 下午10:23
 * Description: 全局登录管理
 */

const val Point_Cut_Is_Login = "execution(@com.black.multi.aoputils.annotation.IsLogin * *(..))"

@Aspect
class LoginAspect {

    @Pointcut(Point_Cut_Is_Login)
    open fun pointCutIsLogin() {}

    @Around("pointCutIsLogin()")
    open fun isLogin(joinPoint: ProceedingJoinPoint) {
        val loginListener = UserManager.instance.isLoginListener
            ?: throw Exception("isLoginListener must be init")

        val signature = joinPoint.signature
        val method = (signature as MethodSignature).method
        val isLoginAnnotation = method.getAnnotation(IsLogin::class.java)
        val isLogin = UserManager.instance.isLogin()
        val loginType = isLoginAnnotation.loginType
        if(loginType == Login_Out){
            UserManager.instance.clear()
            loginListener(isLoginAnnotation.loginType)
            joinPoint.proceed()
            return
        }
        if (isLogin) {
            joinPoint.proceed()
        } else {
            loginListener(isLoginAnnotation.loginType)
        }
    }
}