package com.black.multi.videosample.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentCollectBinding
import com.black.multi.videosample.model.CollectChapterData
import com.black.multi.videosample.ui.adapter.CollectAdapter
import com.black.multi.videosample.utils.COLLECT_PAGE
import com.black.multi.videosample.utils.HOME_DETAIL_PAGE
import com.black.multi.videosample.utils.HomeDetailFragment_Title
import com.black.multi.videosample.utils.HomeDetailFragment_Url
import com.black.multi.videosample.viewmodel.CollectVM
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * Created by wei.
 * Date: 2020/6/2 15:50
 * Desc:
 */
@FragmentDestination(pageUrl = COLLECT_PAGE)
class CollectFragment : BaseFragment<FragmentCollectBinding>(), OnRefreshLoadMoreListener {

    private var page = 0
    private lateinit var mAdapter: CollectAdapter
    private lateinit var mBean: CollectChapterData

    override fun beforeInitView(bundle: Bundle?) {

    }

    override fun initView(bundle: Bundle?) {
        ivBack = binding.includeToolbar.ivBack
        initListener()
    }

    private fun initListener() {
        binding.refreshLayout.setEnableLoadMore(true)
        binding.refreshLayout.setOnRefreshLoadMoreListener(this)
    }

    override fun getLayoutId(): Int = R.layout.fragment_collect

    override fun afterInitView(savedInstanceState: Bundle?) {
        initData()
        fillData()
        fetchData()
    }

    private fun initData() {
        binding.includeToolbar.titleTv.text = "我的收藏"
    }

    @SuppressLint("ResourceType")
    private fun fillData() {
        mAdapter = CollectAdapter(this, IRecycleViewCallback<CollectChapterData> { bean, itemView ->
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

    private fun fetchData() {
        CollectVM.instance.collectChapter(page).observe(this, Observer {
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

                }
            }
        })
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        fetchData()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        fetchData()
    }

}