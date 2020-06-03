package com.black.multi.videosample.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentKnowledgeSubListBinding
import com.black.multi.videosample.model.KnowledgeSubList
import com.black.multi.videosample.ui.adapter.KnowledgeListAdapter
import com.black.multi.videosample.utils.HOME_DETAIL_PAGE
import com.black.multi.videosample.utils.HomeDetailFragment_Title
import com.black.multi.videosample.utils.HomeDetailFragment_Url
import com.black.multi.videosample.utils.KNOWLEDGE_LIST_PAGE
import com.black.multi.videosample.viewmodel.KnowledgeVm
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * Created by wei.
 * Date: 2020/5/28 上午9:37
 * Description:
 */

const val KnowledgeListFragment_Cid = "cid"
const val KnowledgeListFragment_Title = "title"

@FragmentDestination(pageUrl = KNOWLEDGE_LIST_PAGE)
class KnowledgeListFragment : BaseFragment<FragmentKnowledgeSubListBinding>(), OnRefreshLoadMoreListener {

    private var page = 0
    private lateinit var mAdapter: KnowledgeListAdapter
    private var cid = 0
    private var title: String? = null
    private lateinit var mBean: KnowledgeSubList

    override fun beforeInitView(bundle: Bundle?) {
        cid = arguments?.getInt(KnowledgeListFragment_Cid)!!
        title = arguments?.getString(KnowledgeListFragment_Title)
    }

    override fun initView(bundle: Bundle?) {
        ivBack = binding.includeToolbar.ivBack
        initListener()
    }

    private fun initListener() {
        binding.refreshLayout.setEnableLoadMore(true)
        binding.refreshLayout.setOnRefreshLoadMoreListener(this)
    }

    override fun getLayoutId(): Int = R.layout.fragment_knowledge_sub_list

    override fun afterInitView(savedInstanceState: Bundle?) {
        initData()
        fillData()
        fetchData()
    }

    private fun initData() {
        binding.includeToolbar.titleTv.text = title
    }

    @SuppressLint("ResourceType")
    private fun fillData() {
        mAdapter = KnowledgeListAdapter(this, IRecycleViewCallback<KnowledgeSubList> { bean, itemView ->
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

    override fun navigateAnimation(): NavOptions? {
        return NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
    }

    private fun fetchData() {
        KnowledgeVm.instance.getKnowledgeList(page, cid).observe(this, Observer {
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