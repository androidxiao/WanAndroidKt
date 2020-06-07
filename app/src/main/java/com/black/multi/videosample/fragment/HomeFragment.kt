package com.black.multi.videosample.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.ui.BaseListFragmentBinding
import com.black.multi.videosample.databinding.FragmentHomeBinding
import com.black.multi.videosample.model.DataX
import com.black.multi.videosample.model.HomeModel
import com.black.multi.videosample.ui.adapter.HomeAdapter
import com.black.multi.videosample.utils.HOME_DETAIL_PAGE
import com.black.multi.videosample.utils.HOME_PAGE
import com.black.multi.videosample.utils.HomeDetailFragment_Title
import com.black.multi.videosample.utils.HomeDetailFragment_Url
import com.black.multi.videosample.viewmodel.BaseViewModel
import com.black.multi.videosample.viewmodel.HomeVm
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = HOME_PAGE, asStartPage = true)
class HomeFragment : BaseListFragmentBinding<FragmentHomeBinding,HomeModel>() {

    private lateinit var mAdapter: HomeAdapter
    private lateinit var mBean:DataX

    companion object {
        val instance = HomeFragment()
    }

    override fun beforeInitView(savedInstanceState: Bundle?) {

    }

    override fun createViewModel(): BaseViewModel<HomeModel> {
        return HomeVm()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mSmartRefreshLayout = binding.refreshLayout
        setTipView(mSmartRefreshLayout)
        super.initViews(savedInstanceState)
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun afterInitView(savedInstanceState: Bundle?) {
        fillData()
    }

    @SuppressLint("ResourceType")
    private fun fillData() {
        binding.bannerView.initView(this)
        mAdapter = HomeAdapter(this, IRecycleViewCallback<DataX> { bean, itemView ->
            run {
                mBean = bean
                navigateToNextPage()
            }
        })
        (binding.recycleView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mAdapter.setHasStableIds(true)
        binding.recycleView.adapter = mAdapter
    }

    override fun nextPageUrl():String?{
        return HOME_DETAIL_PAGE
    }

    override fun getBundle(): Bundle? {
        val bundle = Bundle()
        bundle.putString(HomeDetailFragment_Url,mBean.link)
        bundle.putString(HomeDetailFragment_Title,mBean.title)
        return bundle
    }

    override fun onResume() {
        super.onResume()
        binding.bannerView.startAnimation()
    }

    override fun onPause() {
        super.onPause()
        binding.bannerView.stopAnimation()
    }

    override fun fetchDataLoading() {
    }

    override fun fetchDataError(error: String?) {
    }

    override fun fetchDataSuccess(
        it: Resource<HomeModel>,
        isRefresh: Boolean,
        smartRefreshLayout: SmartRefreshLayout?,
        page: Int
    ) {
        if (page == 0) {
            binding.refreshLayout.finishRefresh()
            mAdapter.setData(it.data?.datas)
        } else {
            binding.refreshLayout.finishLoadMore()
            mAdapter.addData(it.data?.datas)
        }
    }


}