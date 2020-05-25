package com.black.multi.videosample.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.databinding.FragmentHomeBinding
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.model.Banner
import com.black.multi.videosample.net.Resource
import com.black.multi.videosample.net.Status
import com.black.multi.videosample.utils.GsonUtils
import com.black.multi.videosample.viewmodel.HomeVm
import io.reactivex.Observable

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = "main/tab/home", asStartPage = true)
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    companion object{
        val instance = HomeFragment()
    }

    private val vm: HomeVm by viewModels()

    override fun beforeInitView(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun afterInitView(savedInstanceState: Bundle?) {
        vm.loadData().observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    Log.d("ez","--->"+GsonUtils.getGson().toJson(it.data))
                }
                Status.ERROR -> {
                    Log.d("ez","--->"+it.msg)
                }
            }
        })
    }


}