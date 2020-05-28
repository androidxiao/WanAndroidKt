package com.black.multi.videosample.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.databinding.FragmentProjectBinding
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.utils.GsonUtils
import com.black.multi.videosample.utils.PROJECT_LIST_PAGE
import com.black.multi.videosample.utils.PROJECT_PAGE
import com.black.multi.videosample.viewmodel.ProjectVm
import com.black.xcommon.utils.EzLog

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = PROJECT_LIST_PAGE,asStartPage = false)
class ProjectListFragment(id:Int) : BaseFragment<FragmentProjectBinding>() {

    companion object {
        fun getInstance(id:Int):Fragment{
            val instance = ProjectListFragment(id)
            val bundle = Bundle()
            bundle.putInt("id",id)
            instance.arguments = bundle
            return instance
        }
    }


    override fun beforeInitView(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {

        var id = arguments?.getInt("id",-1)
        EzLog.d("id----->${id}")
    }

    override fun getLayoutId(): Int = R.layout.fragment_project_list


    override fun afterInitView(savedInstanceState: Bundle?) {

        ProjectVm.instance.getProjectTitle().observe(this , Observer {
            when (it.status) {
                Status.LOADING ->{

                }

                Status.SUCCESS ->{
                    val data = it.data

                }

                Status.ERROR ->{

                }
            }
        })
    }


}