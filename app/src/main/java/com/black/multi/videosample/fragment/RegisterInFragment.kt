package com.black.multi.videosample.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentSignUpLayoutBinding
import com.black.multi.videosample.utils.AppConfig
import com.black.multi.videosample.utils.LOGIN_IN_PAGE
import com.black.multi.videosample.utils.REGISTER_PAGE
import com.black.multi.videosample.utils.toast
import com.black.multi.videosample.viewmodel.LoginVM
import com.black.xcommon.utils.EzLog
import com.black.xcommon.utils.GsonUtils

/**
 * Created by wei.
 * Date: 2020/6/2 10:13
 * Desc:
 */
@FragmentDestination(pageUrl = REGISTER_PAGE)
class RegisterInFragment : BaseFragment<FragmentSignUpLayoutBinding>(),View.OnClickListener {
    override fun beforeInitView(bundle: Bundle?) {
    }

    override fun initView(bundle: Bundle?) {
       initListener()
    }

    override fun initListener():Array<Int>{
        return arrayOf(R.id.btn_register,R.id.btn_to_login)
    }

    private fun toLogin(){
        val destination = AppConfig.getDestConfig()!![LOGIN_IN_PAGE]
        val navOptions = NavOptions.Builder().setEnterAnim(R.anim.rotate_fg_enter_right)
                .setExitAnim(R.anim.rotate_fg_exit_left)
                .setPopEnterAnim(R.anim.rotate_fg_exit_right)
                .setPopExitAnim(R.anim.rotate_fg_exit_right)
                .build()
        Navigation.findNavController(activity as FragmentActivity,R.id.btn_to_login).navigate(destination!!.id,null,navOptions)
    }

    private fun register() {
        val userName = binding.etUsername.text.toString()
        val pwd = binding.etPwd.text.toString()
        val pwd2 = binding.etPwd2.text.toString()
        if (TextUtils.isEmpty(userName)) {
            activity?.toast("用户名不能为空")
            return
        }
        if (TextUtils.isEmpty(pwd)) {
            activity?.toast("密码不能为空")
            return
        }

        if (TextUtils.isEmpty(pwd2)) {
            activity?.toast("验证密码不能为空")
            return
        }

        LoginVM.instance.login(userName, pwd).observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    val data = GsonUtils.getGson().toJson(it.data)
                    EzLog.d("data--->${data}")
                }
                Status.ERROR -> {

                }
            }
        })
    }

    override fun afterInitView(bundle: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_sign_up_layout

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_register -> {
                register()
            }
            R.id.btn_to_login->{
                toLogin()
            }
        }
    }
}