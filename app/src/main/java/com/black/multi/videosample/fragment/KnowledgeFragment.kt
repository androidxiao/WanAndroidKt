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
import com.black.multi.videosample.databinding.FragmentKnowledgeBinding
import com.black.multi.videosample.model.KnowledgeModel
import com.black.multi.videosample.ui.adapter.KnowledgeAdapter
import com.black.multi.videosample.utils.KNOWLEDGE_PAGE
import com.black.multi.videosample.viewmodel.KnowledgeVm
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = KNOWLEDGE_PAGE,asStartPage = false)
class KnowledgeFragment : BaseFragment<FragmentKnowledgeBinding>(),OnRefreshListener {

    private lateinit var mAdapter: KnowledgeAdapter

    override fun beforeInitView(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_knowledge

    override fun afterInitView(savedInstanceState: Bundle?) {
        fillData()
        fetchData()
    }

    @SuppressLint("ResourceType")
    private fun fillData() {
        mAdapter = KnowledgeAdapter(this, IRecycleViewCallback<KnowledgeModel> { bean, itemView ->
        })
        (binding.recycleView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mAdapter.setHasStableIds(true)
        binding.recycleView.adapter = mAdapter
    }

    private fun fetchData(){
        KnowledgeVm.instance.getKnowledge().observe(this, Observer {
            when (it.status) {
                Status.LOADING->{

                }
                Status.SUCCESS->{
                    binding.refreshLayout.finishRefresh()
                    val data = it.data
                    mAdapter.setData(data)
                }
                Status.ERROR->{

                }
            }
        })
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        fetchData()
    }


}