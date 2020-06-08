package com.black.multi.videosample.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.black.multi.aoputils.annotation.IsLogin
import com.black.multi.aoputils.utils.User.isLogin
import com.black.multi.aoputils.utils.User.isNightModel
import com.black.multi.aoputils.utils.User.userScore
import com.black.multi.aoputils.utils.UserManager
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentPersonBinding
import com.black.multi.videosample.model.PersonalScoreModel
import com.black.multi.videosample.ui.MainActivity
import com.black.multi.videosample.utils.*
import com.black.multi.videosample.viewmodel.LoginVM
import com.black.xcommon.utils.EzLog
import com.black.xcommon.utils.GsonUtils
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * Created by wei.
 * Date: 2020/5/26 上午10:49
 * Description:
 */

const val alphaFrom = 1.0f
const val alphaTo = 0.5f

@FragmentDestination(pageUrl = PERSON_PAGE)
class PersonFragment : BaseFragment<FragmentPersonBinding>(), View.OnClickListener {

    override fun beforeInitView(bundle: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
        showByLoginStatus()
    }

    private fun showByLoginStatus() {
        binding.btnToLogin.visibility = if (UserManager.instance.isLogin()) View.GONE else View.VISIBLE
        binding.tvName.visibility = if (UserManager.instance.isLogin()) View.VISIBLE else View.GONE
        binding.tvDj.visibility = if (UserManager.instance.isLogin()) View.VISIBLE else View.GONE
        binding.tvJf.visibility = if (UserManager.instance.isLogin()) View.VISIBLE else View.GONE
    }

    override fun initListener(): Array<Int>? {
        return arrayOf(R.id.btn_to_login, R.id.my_collect, R.id.login_out, R.id.isv_model_change, R.id.isv_score_rank)
    }

    override fun getLayoutId(): Int = R.layout.fragment_person

    override fun afterInitView(savedInstanceState: Bundle?) {
        getLiveDataBusEvent()
    }

    private fun getLiveDataBusEvent() {
        LiveEventBus.get(Is_Login).observe(this, Observer {
            binding.btnToLogin.visibility = View.GONE
            EzLog.d("存储的个人信息----->${it.toString()}")
            UserManager.instance.saveLoginUserInfo(it.toString())
            getPersonalScore()
        })
        if (isLogin) {
            val userInfo = GsonUtils.getGson().fromJson<PersonalScoreModel>(userScore,PersonalScoreModel::class.java)
            setUserInfo(userInfo)
            getPersonalScore()
        } else {
            showByLoginStatus()
        }
    }

    //登录成功后获取个人信息
    private fun getPersonalScore() {
        LoginVM.instance.getPersonalScore().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val data = it.data
                    userScore = GsonUtils.getGson().toJson(data)
                    setUserInfo(data!!)
                    showByLoginStatus()
                }
            }
        })
    }

    private fun setUserInfo(data :PersonalScoreModel){
        binding.tvJf.text = "积分 ${data?.coinCount}"
        binding.tvDj.text = "等级 ${data?.rank}"
        binding.tvName.text = data?.username
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_to_login -> {
                navigate(LOGIN_IN_PAGE)
            }

            R.id.my_collect -> {
                toCollect()
            }
            R.id.login_out -> {
                loginOut()
            }
            R.id.isv_model_change -> {
                changeModel()
            }
            R.id.isv_score_rank -> {
                toScoreRank()
            }
        }
    }

    @IsLogin
    private fun toScoreRank() {
        navigate(SCORE_RANK_PAGE)
    }

    /**
     * 仿今日头条夜间模式
     */
    private fun changeModel() {
        if (isNightModel) {
            binding.isvModelChange.setTitle("日间模式")
            (activity as MainActivity).nightOrDay(alphaTo, alphaFrom)
        } else {
            binding.isvModelChange.setTitle("夜间模式")
            (activity as MainActivity).nightOrDay(alphaFrom, alphaTo)
        }
        isNightModel = !isNightModel
    }

    @IsLogin(-1)
    private fun loginOut() {
        LoginVM.instance.loginOut().observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    clearPersonalInfo()
                }
                Status.SUCCESS -> {
                    clearPersonalInfo()
                }
            }
        })
    }

    private fun clearPersonalInfo() {
        UserManager.instance.clear()
        showByLoginStatus()
    }

    @IsLogin
    private fun toCollect() {
        navigate(COLLECT_PAGE)
    }


}