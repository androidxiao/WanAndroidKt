package com.black.multi.videosample.base.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.viewmodel.BaseViewModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

/**
 * Created by wei.
 * Date: 2020/6/5 下午10:26
 * Description:
 */
abstract class BaseListFragmentBinding<B : ViewDataBinding,T> :BaseFragment<B>(), OnRefreshListener,
    OnLoadMoreListener {

    protected var page = 0
    protected var isRefresh = true
    protected lateinit var mSmartRefreshLayout:SmartRefreshLayout
    protected lateinit var mViewModel: BaseViewModel<T>

    protected open fun initViews(
        savedInstanceState: Bundle?
    ) {
        mViewModel = createViewModel()
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.setOnRefreshListener(this)
            mSmartRefreshLayout.setOnLoadMoreListener(this)
            mSmartRefreshLayout.setEnableLoadMore(true)
            showLoading()
            mSmartRefreshLayout.autoRefresh()
        }
    }

    protected abstract fun createViewModel(): BaseViewModel<T>

    /**
     * 刷新
     *
     * @param refreshLayout
     */
    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        isRefresh = true
        mViewModel.page = 0
        fetchData()
    }

    private fun fetchData() {
        mViewModel.getModels().observe(this,
            Observer {
                when (it.status) {
                    Status.LOADING -> if (isRefresh) {
                        fetchDataLoading()
                    }
                    Status.SUCCESS -> {
                        mSmartRefreshLayout.finishRefresh()
                        showContent()
                        stopLoadData()
                        fetchDataSuccess(it, isRefresh, mSmartRefreshLayout, page)
                    }
                    Status.ERROR -> {
                        onRefreshFailure(it.msg)
                        fetchDataError(it.msg)
                    }
                }
            })
    }

    protected open fun fetchDataLoading(){}

    protected open fun fetchDataError(error: String?){}

    private fun stopLoadData() {
        if (isRefresh) {
            mSmartRefreshLayout.finishRefresh()
        } else {
            mSmartRefreshLayout.finishLoadMore()
        }
    }

    /**
     * 加载更多
     *
     * @param refreshLayout
     */
    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        isRefresh = false
        mViewModel.page = page
        fetchData()
    }

    protected abstract fun fetchDataSuccess(
        resource: Resource<T>,
        isRefresh: Boolean,
        smartRefreshLayout: SmartRefreshLayout?,
        page: Int
    )
}