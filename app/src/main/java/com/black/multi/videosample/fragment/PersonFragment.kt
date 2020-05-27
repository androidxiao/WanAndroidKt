package com.black.multi.videosample.fragment

import android.os.Bundle
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.databinding.FragmentProjectBinding
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.utils.PERSON_PAGE

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = PERSON_PAGE,asStartPage = false)
class PersonFragment : BaseFragment<FragmentProjectBinding>() {

    override fun beforeInitView(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_person

    override fun afterInitView(savedInstanceState: Bundle?) {
    }


}