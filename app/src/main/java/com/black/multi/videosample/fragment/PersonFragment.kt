package com.black.multi.videosample.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.black.multi.aoputils.annotation.IsLogin
import com.black.multi.aoputils.utils.UserManager
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentPersonBinding
import com.black.multi.videosample.utils.*
import com.black.multi.videosample.viewmodel.LoginVM
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
    }

    private fun isLogin(){
        binding.btnToLogin.visibility = if(UserManager.instance.isLogin())  View.GONE else View.VISIBLE
        binding.tvDj.visibility = if(UserManager.instance.isLogin())  View.VISIBLE else View.GONE
        binding.tvJf.visibility = if(UserManager.instance.isLogin())  View.VISIBLE else View.GONE
    }

    override fun initListener():Array<Int>?{
        return arrayOf(R.id.btn_to_login,R.id.my_collect,R.id.login_out)
    }

    override fun getLayoutId(): Int = R.layout.fragment_person

    override fun afterInitView(savedInstanceState: Bundle?) {
        getLiveDataBusEvent()
    }

    private fun getLiveDataBusEvent(){
        LiveEventBus.get(Is_Login).observe(this, Observer{
            getPersonalScore()
            binding.btnToLogin.visibility = View.GONE
            binding.tvName.text = userName()
            UserManager.instance.saveLoginUserInfo(it.toString())
        })
    }

    //登录成功后获取个人信息
    private fun getPersonalScore(){
        LoginVM.instance.getPersonalScore().observe(this, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    val data = it.data
                    binding.tvJf.text = "${data?.coinCount}+\"\""
                    binding.tvDj.text = "${data?.rank}+\"\""
                }
            }
        })
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_to_login -> {
                navigate(LOGIN_IN_PAGE)
            }

            R.id.my_collect -> {
                toCollect()
            }
            R.id.login_out->{
                loginOut()
            }
        }
    }
    @IsLogin(-1)
    private fun loginOut(){
        LoginVM.instance.loginOut().observe(this, Observer {
            when(it.status){
                Status.SUCCESS->{
                    isLogin()
                }
            }
        })
    }

    @IsLogin()
    private fun toCollect(){
        navigate(COLLECT_PAGE)
    }


}