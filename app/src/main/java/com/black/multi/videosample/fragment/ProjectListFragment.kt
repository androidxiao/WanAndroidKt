package com.black.multi.videosample.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentProjectListBinding
import com.black.multi.videosample.model.ProjectListData
import com.black.multi.videosample.ui.adapter.ProjectListAdapter
import com.black.multi.videosample.utils.*
import com.black.multi.videosample.viewmodel.ProjectVm
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = PROJECT_LIST_PAGE,asStartPage = false)
class ProjectListFragment(id:Int) : BaseFragment<FragmentProjectListBinding>(),
    OnRefreshLoadMoreListener {

    private var cid : Int= 0
    private var page = 1
    private lateinit var mAdapter: ProjectListAdapter

    companion object {
        fun getInstance(id:Int):Fragment{
            val instance = ProjectListFragment(id)
            val bundle = Bundle()
            bundle.putInt("id",id)
            instance.arguments = bundle
            return instance
        }
    }


    override fun beforeInitView(savedInstanceState: Bundle?) {

    }

    override fun initView(bundle: Bundle?) {
        cid = arguments!!.getInt("id",-1)
        initListener()
    }

    private fun initListener(){
        binding.refreshLayout.setEnableLoadMore(true)
        binding.refreshLayout.setOnRefreshLoadMoreListener(this)
    }

    override fun getLayoutId(): Int = R.layout.fragment_project_list


    override fun afterInitView(savedInstanceState: Bundle?) {
        fillData()
        fetchData()
    }
    private fun fillData() {
        mAdapter = ProjectListAdapter(this, IRecycleViewCallback<ProjectListData> { bean, itemView ->
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
        ProjectVm.instance.getProjectList(page,cid).observe(this, Observer {
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