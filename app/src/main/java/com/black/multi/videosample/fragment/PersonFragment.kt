package com.black.multi.videosample.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentPersonBinding
import com.black.multi.videosample.utils.AppConfig
import com.black.multi.videosample.utils.COLLECT_PAGE
import com.black.multi.videosample.utils.LOGIN_IN_PAGE
import com.black.multi.videosample.utils.PERSON_PAGE
import com.black.xcommon.utils.EzLog

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

        initListener()
    }

    private fun initListener() {
        binding.btnToLogin.setOnClickListener(this)
        binding.myCollect.setOnClickListener(this)
    }

    override fun getLayoutId(): Int = R.layout.fragment_person

    override fun afterInitView(savedInstanceState: Bundle?) {
    }

    private fun navigate(destination:String) {
        val destination = AppConfig.getDestConfig()!![destination]
        findNavController(this).navigate(destination!!.id)
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