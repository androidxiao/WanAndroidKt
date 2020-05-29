package com.black.multi.videosample.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.databinding.FragmentNavBinding
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.model.Article
import com.black.multi.videosample.model.KnowledgeModel
import com.black.multi.videosample.model.NavModel
import com.black.multi.videosample.ui.adapter.KnowledgeAdapter
import com.black.multi.videosample.ui.adapter.NavContentAdapter
import com.black.multi.videosample.ui.adapter.NavTitleAdapter
import com.black.multi.videosample.utils.NAV_PAGE
import com.black.multi.videosample.viewmodel.KnowledgeVm
import com.black.multi.videosample.viewmodel.NavVm
import com.black.multi.videosample.widget.TopItemDecoration
import com.black.xcommon.utils.EzLog
import com.scwang.smartrefresh.layout.api.RefreshLayout

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = NAV_PAGE,asStartPage = false)
class NavFragment : BaseFragment<FragmentNavBinding>() {

    private  val titles = ArrayList<String>()
    private lateinit var mTitleAdapter: NavTitleAdapter
    private lateinit var mContentAdapter: NavContentAdapter

    override fun beforeInitView(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_nav

    override fun afterInitView(savedInstanceState: Bundle?) {
        fillData()
        fetchData()
    }

    private fun fillData() {
        mTitleAdapter = NavTitleAdapter(this, IRecycleViewCallback<String> { bean, itemView ->
        })
        (binding.leftRv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mTitleAdapter.setHasStableIds(true)
        binding.leftRv.adapter = mTitleAdapter

        mContentAdapter = NavContentAdapter(this, IRecycleViewCallback<Article> { bean, itemView ->
        })

        (binding.leftRv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mContentAdapter.setHasStableIds(true)
        binding.rightRv.adapter = mContentAdapter
    }

    private fun fetchData(){
        NavVm.instance.getNavData().observe(this, Observer {
            when (it.status) {
                Status.LOADING->{

                }
                Status.SUCCESS->{
                    val data = it.data!!
                    showTitle(data)
                }
                Status.ERROR->{

                }
            }
        })
    }

    private fun showTitle(data:List<NavModel>){

        val contents = ArrayList<Article>()
        for(i in data.indices){
            var model = data[i]
            titles.add(model.name)
            contents.addAll(data[i].articles)
        }
        mTitleAdapter.setData(titles)

        mContentAdapter.setData(contents)

        val topDecoration = TopItemDecoration(context as Activity).apply {
            this.tagListener = {
//                titles[it]
                EzLog.d("it--->${it}")
                ""
            }
        }
        binding.rightRv.addItemDecoration(topDecoration)
    }

}