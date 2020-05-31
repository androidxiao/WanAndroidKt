package com.black.multi.videosample.fragment

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentNavBinding
import com.black.multi.videosample.model.NavModel
import com.black.multi.videosample.ui.adapter.NavContentAdapter
import com.black.multi.videosample.ui.adapter.NavTitleAdapter
import com.black.multi.videosample.utils.NAV_PAGE
import com.black.multi.videosample.viewmodel.NavVm
import com.black.multi.videosample.widget.TopItemDecoration

/**
 * Created by wei.
 * Date: 2020/5/29 下午3:49
 * Description:
 */
@FragmentDestination(pageUrl = NAV_PAGE, asStartPage = false)
class NavFragment : BaseFragment<FragmentNavBinding>() {

    private val titles = ArrayList<String>()
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
        //左边联动右边
        val manager = binding.rightRv.layoutManager as LinearLayoutManager
        mTitleAdapter = NavTitleAdapter().apply {
            onItemClickListener={
                mTitleAdapter.setChoose(it)
                manager.scrollToPositionWithOffset(it, 0)
            }
        }
        (binding.leftRv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mTitleAdapter.setHasStableIds(true)
        binding.leftRv.adapter = mTitleAdapter


        mContentAdapter = NavContentAdapter(this, IRecycleViewCallback<NavModel> { bean, itemView ->
        })
        (binding.leftRv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mContentAdapter.setHasStableIds(true)
        binding.rightRv.adapter = mContentAdapter

        //右边联动左边
        binding.rightRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstItemPosition = manager.findFirstVisibleItemPosition()
                if (firstItemPosition != -1) {
                    binding.leftRv.smoothScrollToPosition(firstItemPosition)
                    mTitleAdapter.setChoose(firstItemPosition)
                }
            }

        })
    }

    private fun fetchData() {
        NavVm.instance.getNavData().observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    val data = it.data!!
                    showTitle(data)
                }
                Status.ERROR -> {

                }
            }
        })
    }

    private fun showTitle(data: List<NavModel>) {

        for (i in data.indices) {
            var model = data[i]
            titles.add(model.name)
        }

        mTitleAdapter.setDatas(titles)

        mContentAdapter.setData(data)

        val topDecoration = TopItemDecoration(context as Activity).apply {
            tagListener = {
                titles[it]
            }
        }
        binding.rightRv.addItemDecoration(topDecoration)
    }

}