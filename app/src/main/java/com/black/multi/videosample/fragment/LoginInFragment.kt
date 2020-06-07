package com.black.multi.videosample.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentLoginInBinding
import com.black.multi.videosample.utils.Is_Login
import com.black.multi.videosample.utils.LOGIN_IN_PAGE
import com.black.multi.videosample.utils.REGISTER_PAGE
import com.black.multi.videosample.utils.toast
import com.black.multi.videosample.viewmodel.LoginVM
import com.black.xcommon.utils.EzLog
import com.black.xcommon.utils.GsonUtils
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * Created by wei.
 * Date: 2020/6/1 14:19
 * Desc:
 */
@FragmentDestination(pageUrl = LOGIN_IN_PAGE)
class LoginInFragment : BaseFragment<FragmentLoginInBinding>(), View.OnClickListener {
    override fun beforeInitView(bundle: Bundle?) {
    }

    override fun initView(bundle: Bundle?) {
        initListener()

    }

    override fun afterInitView(bundle: Bundle?) {
    }

    override fun initListener():Array<Int> {
        return arrayOf(R.id.id_btn_login,R.id.btn_to_register)
    }

    private fun toRegister() {
        navigateToNextPage()
    }

    override fun nextPageUrl(): String? {
        return REGISTER_PAGE
    }

    override fun navigateAnimation(): NavOptions? {
        return NavOptions.Builder()
                .setEnterAnim(R.anim.rotate_fg_enter_left)
                .setExitAnim(R.anim.rotate_fg_exit_right)
                .setPopEnterAnim(R.anim.rotate_fg_exit_right)
                .setPopExitAnim(R.anim.rotate_fg_exit_left)
                .build()
    }

    private fun login() {
        val userName = binding.etUsername.text.toString()
        val pwd = binding.etPwd.text.toString()
        if (TextUtils.isEmpty(userName)) {
            activity?.toast("用户名不能为空")
            return
        }
        if (TextUtils.isEmpty(pwd)) {
            activity?.toast("密码不能为空")
            return
        }

        LoginVM.instance.login(userName, pwd).observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    EzLog.d("data--->开始登录")
                }
                Status.SUCCESS -> {
                    val data = GsonUtils.getGson().toJson(it.data)
                    LiveEventBus.get(Is_Login).post(data)
                    fgPopBack()
                    EzLog.d("data--->${data}")
                }
                Status.ERROR -> {
                    EzLog.d("data--->${it.msg}")
                }
            }
        })
    }

    override fun getLayoutId(): Int = R.layout.fragment_login_in

    override fun onClick(v: View) {
        when (v.id) {
            R.id.id_btn_login -> {
                login()
            }
            R.id.btn_to_register->{
                toRegister()
            }
        }
    }
}