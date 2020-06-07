package com.black.multi.videosample.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.ui.BaseListFragmentBinding
import com.black.multi.videosample.databinding.FragmentProjectListBinding
import com.black.multi.videosample.model.ProjectListData
import com.black.multi.videosample.model.ProjectListModel
import com.black.multi.videosample.ui.adapter.ProjectListAdapter
import com.black.multi.videosample.utils.HOME_DETAIL_PAGE
import com.black.multi.videosample.utils.HomeDetailFragment_Title
import com.black.multi.videosample.utils.HomeDetailFragment_Url
import com.black.multi.videosample.utils.PROJECT_LIST_PAGE
import com.black.multi.videosample.viewmodel.BaseViewModel
import com.black.multi.videosample.viewmodel.ProjectVm
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * Created by wei.
 * Date: 2020/5/27 上午10:49
 * Description:
 */
@FragmentDestination(pageUrl = PROJECT_LIST_PAGE)
class ProjectListFragment(id:Int) : BaseListFragmentBinding<FragmentProjectListBinding,ProjectListModel>(){

    private var cid : Int= 0
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
        cid = arguments!!.getInt("id",-1)
    }

    override fun initView(bundle: Bundle?) {
        mSmartRefreshLayout = binding.refreshLayout
        setTipView(mSmartRefreshLayout)
        super.initViews(bundle)
    }

    override fun getLayoutId(): Int = R.layout.fragment_project_list


    override fun afterInitView(savedInstanceState: Bundle?) {
        fillData()
    }
    private fun fillData() {
        mAdapter = ProjectListAdapter(this, IRecycleViewCallback<ProjectListData> { bean, itemView ->
            run {
                val bundle = Bundle()
                bundle.putString(HomeDetailFragment_Url,bean.link)
                bundle.putString(HomeDetailFragment_Title,bean.title)
                navigate(HOME_DETAIL_PAGE,bundle,null)
            }
        })
        (binding.recycleView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mAdapter.setHasStableIds(true)
        binding.recycleView.adapter = mAdapter
    }

    override fun createViewModel(): BaseViewModel<ProjectListModel> {
        val vm  = ProjectVm()
        vm.cid = cid
        return vm
    }

    override fun fetchDataSuccess(
        it: Resource<ProjectListModel>,
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