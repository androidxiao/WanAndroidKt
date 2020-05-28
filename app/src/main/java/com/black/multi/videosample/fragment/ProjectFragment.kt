package com.black.multi.videosample.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentProjectBinding
import com.black.multi.videosample.utils.PROJECT_PAGE
import com.black.multi.videosample.viewmodel.ProjectVm

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = PROJECT_PAGE,asStartPage = false)
class ProjectFragment : BaseFragment<FragmentProjectBinding>() {
    
    override fun beforeInitView(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun afterInitView(savedInstanceState: Bundle?) {

        ProjectVm.instance.getProjectTitle().observe(this , Observer {
            when (it.status) {
                Status.LOADING ->{

                }

                Status.SUCCESS ->{
                    val data = it.data
//                    EzLog.d("data--->${GsonUtils.getGson().toJson(data)}")
                    binding.tabView.initView(data!!,this,lifecycle)
                }

                Status.ERROR ->{

                }
            }
        })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        val fragments =
            childFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment.isAdded && fragment.isVisible) {
                fragment.onHiddenChanged(hidden)
                break
            }
        }
    }

}