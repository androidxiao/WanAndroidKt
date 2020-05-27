package com.black.multi.videosample.fragment

import android.os.Bundle
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.databinding.FragmentNavBinding
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.utils.NAV_PAGE

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = NAV_PAGE,asStartPage = false)
class NavFragment : BaseFragment<FragmentNavBinding>() {

    override fun beforeInitView(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_nav

    override fun afterInitView(savedInstanceState: Bundle?) {
    }


}