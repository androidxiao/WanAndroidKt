package com.black.multi.videosample.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentCollectBinding
import com.black.multi.videosample.model.CollectChapterData
import com.black.multi.videosample.model.KnowledgeSubList
import com.black.multi.videosample.ui.adapter.CollectAdapter
import com.black.multi.videosample.ui.adapter.KnowledgeListAdapter
import com.black.multi.videosample.utils.*
import com.black.multi.videosample.viewmodel.CollectVM
import com.black.multi.videosample.viewmodel.KnowledgeVm
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * Created by wei.
 * Date: 2020/6/2 15:50
 * Desc:
 */
@FragmentDestination(pageUrl = COLLECT_PAGE)
class CollectFragment :BaseFragment<FragmentCollectBinding>(), OnRefreshLoadMoreListener {

    private var page = 0
    private lateinit var mAdapter: CollectAdapter

    override fun beforeInitView(bundle: Bundle?) {

    }

    override fun initView(bundle: Bundle?) {
        ivBack = binding.includeToolbar.ivBack
        initListener()
    }

    private fun initListener(){
        binding.refreshLayout.setEnableLoadMore(true)
        binding.refreshLayout.setOnRefreshLoadMoreListener(this)
    }

    override fun getLayoutId(): Int = R.layout.fragment_collect

    override fun afterInitView(savedInstanceState: Bundle?) {
        initData()
        fillData()
        fetchData()
    }

    private fun initData(){
        binding.includeToolbar.titleTv.text = "我的收藏"
    }

    @SuppressLint("ResourceType")
    private fun fillData() {
        mAdapter = CollectAdapter(this, IRecycleViewCallback<CollectChapterData> { bean, itemView ->
            run {
                //            findNavController(this).navigate(R.id.navigation_dashboard)
                val destination = AppConfig.getDestConfig()!![HOME_DETAIL_PAGE]
                val bundle = Bundle()
                bundle.putString(HomeDetailFragment_Url,bean.link)
                bundle.putString(HomeDetailFragment_Title,bean.title)
                NavHostFragment.findNavController(this).navigate(destination!!.id,bundle)
            }
        })
        (binding.recycleView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mAdapter.setHasStableIds(true)
        binding.recycleView.adapter = mAdapter
    }

    private fun fetchData(){
        CollectVM.instance.collectChapter(page).observe(this, Observer {
            when (it.status) {
                Status.LOADING->{

                }
                Status.SUCCESS->{
                    if (page == 0) {
                        binding.refreshLayout.finishRefresh()
                        mAdapter.setData(it.data?.datas)
                    } else {
                        binding.refreshLayout.finishLoadMore()
                        mAdapter.addData(it.data?.datas)
                    }
                }
                Status.ERROR->{

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