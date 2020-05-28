package com.black.multi.videosample.fragment

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.databinding.FragmentProjectBinding
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.utils.GsonUtils
import com.black.multi.videosample.utils.PROJECT_PAGE
import com.black.multi.videosample.viewmodel.ProjectVm
import com.black.multi.videosample.widget.ProjectTabView
import com.black.xcommon.utils.EzLog

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
                    val tabView = ProjectTabView(activity as FragmentActivity)
                    tabView.initView(data,this)
                    binding.rl.addView(tabView)
                }

                Status.ERROR ->{

                }
            }
        })
    }


}