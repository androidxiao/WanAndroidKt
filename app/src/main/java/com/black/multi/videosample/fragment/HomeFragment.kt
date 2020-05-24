package com.black.multi.videosample.fragment

import android.os.Bundle
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.databinding.FragmentHomeBinding
import com.black.multi.videosample.ui.base.ui.BaseFragment

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = "main/tab/home",asStartPage = true)
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun beforeInitView(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun afterInitView(savedInstanceState: Bundle?) {
    }


}