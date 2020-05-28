package com.black.multi.videosample.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentHomeBinding
import com.black.multi.videosample.model.DataX
import com.black.multi.videosample.ui.adapter.HomeAdapter
import com.black.multi.videosample.utils.*
import com.black.multi.videosample.viewmodel.HomeVm
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotyoxutils.EzLog


/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = HOME_PAGE, asStartPage = true)
class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnRefreshLoadMoreListener {

    private var page = 0
    private lateinit var mAdapter: HomeAdapter

    companion object {
        val instance = HomeFragment()
    }

    private val vm: HomeVm by viewModels()

    override fun beforeInitView(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.refreshLayout.setEnableLoadMore(true)
        binding.refreshLayout.setOnRefreshLoadMoreListener(this)
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun afterInitView(savedInstanceState: Bundle?) {
        fillData()
        fetchData()
    }

    @SuppressLint("ResourceType")
    private fun fillData() {
        binding.bannerView.initView(this)
        mAdapter = HomeAdapter(this, IRecycleViewCallback<DataX> { bean, itemView ->
            run {
                val destination = AppConfig.getDestConfig()!![HOME_DETAIL_PAGE]
                val bundle = Bundle()
                bundle.putString(HomeDetailFragment_Url,bean.link)
                bundle.putString(HomeDetailFragment_Title,bean.title)
                findNavController(this).navigate(destination!!.id,bundle)
                EzLog.d("${bean.title}${destination?.id}")
            }
        })
        (binding.recycleView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mAdapter.setHasStableIds(true)
        binding.recycleView.adapter = mAdapter
    }

    private fun fetchData() {
        vm.getHomeData(page).observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    if (page == 0) {
                        binding.refreshLayout.finishRefresh()
                        mAdapter.setData(it.data?.datas)
                    } else {
                        binding.refreshLayout.finishLoadMore()
                        mAdapter.addData(it.data?.datas)
                    }

                }
                Status.ERROR -> {
                    EzLog.d("HomeFg--->" + it.msg)
                }
            }
        })
    }



    override fun onResume() {
        super.onResume()
        binding.bannerView.startAnimation()
    }

    override fun onPause() {
        super.onPause()
        binding.bannerView.stopAnimation()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        fetchData()
    }


    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        fetchData()
    }


}