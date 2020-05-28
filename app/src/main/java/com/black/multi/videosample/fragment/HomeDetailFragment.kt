package com.black.multi.videosample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.base.ui.BaseAgentWebFragment
import com.black.multi.videosample.databinding.FragmentHomeDetailBinding
import com.black.multi.videosample.utils.HOME_DETAIL_PAGE
import com.black.multi.videosample.utils.HomeDetailFragment_Title
import com.black.multi.videosample.utils.HomeDetailFragment_Url
import com.black.multi.videosample.utils.ShowHideBottomBar
import com.black.xcommon.utils.EzLog


/**
 * Created by wei.
 * Date: 2020/5/26 15:41
 * Desc:
 */


@FragmentDestination(pageUrl = HOME_DETAIL_PAGE, asStartPage = false)
class HomeDetailFragment : BaseAgentWebFragment() {
    private lateinit var mBinding: FragmentHomeDetailBinding
    private var url: String? = null
    private var title: String? = null

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate<FragmentHomeDetailBinding>(inflater, R.layout.fragment_home_detail, container, false)
        getBundle()
        initTitle()
        navigationUp()
        return mBinding.root
    }

    private fun navigationUp() {
        mBinding.includeToolbar.toolbar.setNavigationOnClickListener {
            val id = ShowHideBottomBar.instance.getId()
            val isMain = ShowHideBottomBar.instance.getIsMain()
            EzLog.d("HomeDetailFragment--navigationUp--finish--to--page--id--->${ShowHideBottomBar.instance.getId()}----isMain-->${isMain}")
            if (id != null && isMain != null && isMain) {
                NavHostFragment.findNavController(this).popBackStack()
            }
        }
    }

    private fun initTitle(){
        mBinding.includeToolbar.titleTv.text = title
    }

    private fun getBundle() {
        url = arguments?.get(HomeDetailFragment_Url) as String
        title = arguments?.get(HomeDetailFragment_Title) as String
    }

    override fun getAgentWebParent(): ViewGroup {
        return mBinding.linearLayout
    }

    override fun getUrl(): String? {
        return url
    }

}