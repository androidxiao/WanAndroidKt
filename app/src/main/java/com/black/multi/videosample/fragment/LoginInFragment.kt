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
import com.black.multi.videosample.databinding.FragmentLoginInBinding
import com.black.multi.videosample.utils.*
import com.black.multi.videosample.viewmodel.CollectVM
import com.black.multi.videosample.viewmodel.LoginVM
import com.black.xcommon.utils.EzLog

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

    private fun initListener() {
        binding.idBtnLogin.setOnClickListener(this)
        binding.btnToRegister.setOnClickListener(this)
    }

    private fun toRegister() {
        val destination = AppConfig.getDestConfig()!![REGISTER_PAGE]
        val navOptions = NavOptions.Builder().setEnterAnim(R.anim.rotate_fg_enter_left)
                .setExitAnim(R.anim.rotate_fg_exit_right)
                .setPopEnterAnim(R.anim.rotate_fg_exit_right)
                .setPopExitAnim(R.anim.rotate_fg_exit_left)
                .build()
        Navigation.findNavController(activity as FragmentActivity, R.id.btn_to_register).navigate(destination!!.id, null, navOptions)
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
                    EzLog.d("data--->${data}")
                    CollectVM.instance.collectChapter(0).observe(this, Observer {
                        when (it.status) {
                            Status.LOADING->{

                            }
                            Status.SUCCESS->{
                                EzLog.d("collect-->${GsonUtils.getGson().toJson(it.data)}")
                            }
                            Status.ERROR->{
                                EzLog.d("error--->${it.msg}")
                            }
                        }
                    })
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