package com.black.multi.videosample.fragment

import android.os.Bundle
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentLoginInBinding
import com.black.multi.videosample.utils.HOME_DETAIL_PAGE
import com.black.multi.videosample.utils.LOGIN_IN_PAGE

/**
 * Created by wei.
 * Date: 2020/6/1 14:19
 * Desc:
 */
@FragmentDestination(pageUrl = LOGIN_IN_PAGE, asStartPage = false)
class LoginInFragment : BaseFragment<FragmentLoginInBinding>() {
    override fun beforeInitView(bundle: Bundle?) {
    }

    override fun initView(bundle: Bundle?) {
    }

    override fun afterInitView(bundle: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_login_in
}