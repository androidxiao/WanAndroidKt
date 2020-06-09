package com.black.multi.videosample.fragment

import android.os.Bundle
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.ui.BaseListFragmentBinding
import com.black.multi.videosample.databinding.FragmentScoreRankBinding
import com.black.multi.videosample.model.RankScoreDataX
import com.black.multi.videosample.model.RankScoreModel
import com.black.multi.videosample.ui.adapter.ScoreRankAdapter
import com.black.multi.videosample.utils.SCORE_RANK_PAGE
import com.black.multi.videosample.viewmodel.BaseListViewModel
import com.black.multi.videosample.viewmodel.ScoreRankVM
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * Created by wei.
 * Date: 2020/6/2 15:50
 * Desc:
 */
@FragmentDestination(pageUrl = SCORE_RANK_PAGE)
class ScoreRankFragment : BaseListFragmentBinding<FragmentScoreRankBinding, RankScoreModel>() {

    private lateinit var mAdapter: ScoreRankAdapter

    override fun beforeInitView(bundle: Bundle?) {

    }

    override fun initView(bundle: Bundle?) {
        mSmartRefreshLayout = binding.refreshLayout
        setTipView(mSmartRefreshLayout)
        super.initViews(bundle)
        ivBack = binding.includeToolbar.ivBack
    }

    override fun getLayoutId(): Int = R.layout.fragment_score_rank

    override fun afterInitView(savedInstanceState: Bundle?) {
        initData()
        fillData()
    }

    private fun initData() {
        binding.includeToolbar.titleTv.text = "积分排行"
    }

    private fun fillData() {
        mAdapter = ScoreRankAdapter(this, IRecycleViewCallback<RankScoreDataX> { bean, itemView ->
        })
        (binding.recycleView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mAdapter.setHasStableIds(true)
        binding.recycleView.adapter = mAdapter
    }


    override fun createViewModel(): BaseListViewModel<RankScoreModel> {
        return ScoreRankVM()
    }

    override fun fetchDataLoading() {
    }

    override fun fetchDataError(error: String?) {
    }

    override fun fetchDataSuccess(
            it: Resource<RankScoreModel>,
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