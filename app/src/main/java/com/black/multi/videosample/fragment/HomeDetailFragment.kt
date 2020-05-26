package com.black.multi.videosample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.base.ui.BaseAgentWebFragment
import com.black.multi.videosample.databinding.FragmentHomeBinding
import com.black.multi.videosample.databinding.FragmentHomeDetailBinding


/**
 * Created by wei.
 * Date: 2020/5/26 15:41
 * Desc:
 */
@FragmentDestination(pageUrl = "main/tab/home/homedetail", asStartPage = false)
class HomeDetailFragment :BaseAgentWebFragment() {

    lateinit var mBinding: FragmentHomeDetailBinding

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate<FragmentHomeDetailBinding>(inflater, R.layout.fragment_home_detail,container,false)
        return mBinding.root
    }

    override fun getAgentWebParent(): ViewGroup {
        return mBinding.linearLayout
    }

    override fun getUrl(): String? {
        return "https://www.baidu.com"
    }

}