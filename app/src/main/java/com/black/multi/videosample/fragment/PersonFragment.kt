package com.black.multi.videosample.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentPersonBinding
import com.black.multi.videosample.utils.*
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * Created by wei.
 * Date: 2020/5/26 上午10:49
 * Description:
 */
@FragmentDestination(pageUrl = PERSON_PAGE)
class PersonFragment : BaseFragment<FragmentPersonBinding>(), View.OnClickListener {

    override fun beforeInitView(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
        isLogin()
        initListener()
    }

    private fun isLogin(){
//        binding.btnToLogin.visibility = if(UserManager.instance.isLogin())  View.GONE else View.VISIBLE
        binding.tvName.visibility = if(UserManager.instance.isLogin())  View.VISIBLE else View.GONE
        binding.tvDj.visibility = if(UserManager.instance.isLogin())  View.VISIBLE else View.GONE
        binding.tvJf.visibility = if(UserManager.instance.isLogin())  View.VISIBLE else View.GONE
    }

    private fun initListener() {
        binding.btnToLogin.setOnClickListener(this)
        binding.myCollect.setOnClickListener(this)
    }

    override fun getLayoutId(): Int = R.layout.fragment_person

    override fun afterInitView(savedInstanceState: Bundle?) {
        getLiveDataBusEvent()
    }

    private fun getLiveDataBusEvent(){
        LiveEventBus.get(Is_Login).observe(this, Observer{
            binding.btnToLogin.visibility = View.GONE
            UserManager.instance.saveLoginUserInfo(it.toString())
        })
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_to_login -> {
                navigate(LOGIN_IN_PAGE)
            }
            R.id.my_collect -> {
                navigate(COLLECT_PAGE)
            }
        }
    }


}