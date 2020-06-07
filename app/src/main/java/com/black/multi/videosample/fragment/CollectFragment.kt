package com.black.multi.videosample.fragment

import android.os.Bundle
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.ui.BaseListFragmentBinding
import com.black.multi.videosample.databinding.FragmentCollectBinding
import com.black.multi.videosample.model.CollectChapterData
import com.black.multi.videosample.model.CollectChapterModel
import com.black.multi.videosample.ui.adapter.CollectAdapter
import com.black.multi.videosample.utils.COLLECT_PAGE
import com.black.multi.videosample.utils.HOME_DETAIL_PAGE
import com.black.multi.videosample.utils.HomeDetailFragment_Title
import com.black.multi.videosample.utils.HomeDetailFragment_Url
import com.black.multi.videosample.viewmodel.BaseViewModel
import com.black.multi.videosample.viewmodel.CollectVM
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * Created by wei.
 * Date: 2020/6/2 15:50
 * Desc:
 */
@FragmentDestination(pageUrl = COLLECT_PAGE)
class CollectFragment : BaseListFragmentBinding<FragmentCollectBinding, CollectChapterModel>() {

    private lateinit var mAdapter: CollectAdapter
    private lateinit var mBean: CollectChapterData

    override fun beforeInitView(bundle: Bundle?) {

    }

    override fun initView(bundle: Bundle?) {
        mSmartRefreshLayout = binding.refreshLayout
        setTipView(mSmartRefreshLayout)
        super.initViews(bundle)
        ivBack = binding.includeToolbar.ivBack
    }

    override fun getLayoutId(): Int = R.layout.fragment_collect

    override fun afterInitView(savedInstanceState: Bundle?) {
        initData()
        fillData()
    }

    private fun initData() {
        binding.includeToolbar.titleTv.text = "我的收藏"
    }

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

    override fun createViewModel(): BaseViewModel<CollectChapterModel> {
        return CollectVM()
    }

    override fun fetchDataLoading() {
    }

    override fun fetchDataError(error: String?) {
    }

    override fun fetchDataSuccess(
        it: Resource<CollectChapterModel>,
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