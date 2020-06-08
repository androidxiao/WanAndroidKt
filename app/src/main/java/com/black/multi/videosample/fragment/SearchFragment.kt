package com.black.multi.videosample.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.ui.BaseListFragmentBinding
import com.black.multi.videosample.databinding.FragmentSearchBinding
import com.black.multi.videosample.model.DataX
import com.black.multi.videosample.model.HomeModel
import com.black.multi.videosample.ui.adapter.HomeAdapter
import com.black.multi.videosample.utils.*
import com.black.multi.videosample.viewmodel.BaseViewModel
import com.black.multi.videosample.viewmodel.SearchVm
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */

const val k = "k"

@FragmentDestination(pageUrl = SEARCH_PAGE)
class SearchFragment : BaseListFragmentBinding<FragmentSearchBinding, HomeModel>() {

    private lateinit var mAdapter: HomeAdapter
    private lateinit var mBean: DataX
    private var key: String? = null

    companion object {
        val instance = SearchFragment()
    }

    override fun beforeInitView(bundle: Bundle?) {
        key = arguments?.getString(k)
    }

    override fun createViewModel(): BaseViewModel<HomeModel> {
        val vm = SearchVm()
        vm.k = key!!
        return vm
    }

    override fun initView(savedInstanceState: Bundle?) {
        mSmartRefreshLayout = binding.refreshLayout
        setTipView(mSmartRefreshLayout)
        super.initViews(savedInstanceState)
        ivBack = binding.includeToolbar.ivBack
    }

    override fun getLayoutId(): Int = R.layout.fragment_search

    override fun afterInitView(savedInstanceState: Bundle?) {
        initData()
        search()
        fillData()
    }

    private fun initData(){
        binding.includeToolbar.etSearch.setText(key)
    }

    private fun search(){
        binding.includeToolbar.tvSearch.setOnClickListener {
            val searchText = binding.includeToolbar.etSearch.text.toString()
            val notEmpty = searchText.isNotEmpty()
            if(notEmpty) {
                (mViewModel as SearchVm).k = searchText
                onRefresh(binding.refreshLayout)
            }else{
                activity?.toast("关键字不能为空!")
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun fillData() {
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

    override fun nextPageUrl(): String? {
        return HOME_DETAIL_PAGE
    }

    override fun getBundle(): Bundle? {
        val bundle = Bundle()
        bundle.putString(HomeDetailFragment_Url, mBean.link)
        bundle.putString(HomeDetailFragment_Title, mBean.title)
        return bundle
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
            mAdapter.setData(it.data?.datas)
        } else {
            mAdapter.addData(it.data?.datas)
        }
    }


}