package com.black.multi.videosample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.databinding.FragmentSearchKeyBinding
import com.black.multi.videosample.model.database.SearchEntity
import com.black.multi.videosample.ui.adapter.SearchAdapter
import com.black.multi.videosample.utils.SEARCH_KEY_PAGE
import com.black.multi.videosample.utils.SEARCH_PAGE
import com.black.multi.videosample.utils.toast
import com.black.multi.videosample.viewmodel.SearchDaoVM
import com.black.multi.videosample.viewmodel.SearchKeyVm
import kotyoxutils.Px2DpUtil.dp2px


/**
 * Created by wei.
 * Date: 2020/6/8 下午15:19
 * Description:
 */
@FragmentDestination(pageUrl = SEARCH_KEY_PAGE)
class SearchKeyFragment : BaseFragment<FragmentSearchKeyBinding>() {

    private var entity = SearchEntity()

    companion object {
        val instance = SearchKeyFragment()
    }

    override fun beforeInitView(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        ivBack = binding.includeToolbar.ivBack
    }

    override fun getLayoutId(): Int = R.layout.fragment_search_key

    override fun afterInitView(savedInstanceState: Bundle?) {
        fillData()
        toSearchList()
    }

    private fun toSearchList() {
        binding.includeToolbar.tvSearch.setOnClickListener {
            val searchText = binding.includeToolbar.etSearch.text.toString()
            canSearch(searchText)
        }
    }

    private fun canSearch(searchText: String) {
        val notEmpty = searchText.isNotEmpty()
        if (notEmpty) {
            val bundle = Bundle()
            bundle.putString(k, searchText)
            entity.searchText = searchText
            SearchDaoVM.instance.insertSearchText(entity).observe(this, Observer {  })
            navigate(SEARCH_PAGE, bundle)
        } else {
            activity?.toast("关键字不能为空!")
        }
    }

    private fun fillData() {
        SearchKeyVm.INSTANCE.getModels().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    for (element in it.data!!) {
                        val tv = LayoutInflater.from(context).inflate(R.layout.custom_hot_search_key, null, false) as TextView
                        tv.text = element.name
                        tv.tag = element.id
                        binding.centerFlowLayout.addView(tv)
                        tv.setOnClickListener {
                            binding.includeToolbar.etSearch.setText(element.name)
                            canSearch(element.name)
                        }
                    }
                    binding.centerFlowLayout.setChildSpacing(dp2px(context, 10.0f))
                    binding.centerFlowLayout.setRowSpacing(dp2px(context, 20.0f))
                }
            }

        })


        SearchDaoVM.instance.getSearchList().observe(this, Observer {
            if (it.isNotEmpty()) {
                val adapter = SearchAdapter(this, IRecycleViewCallback<SearchEntity> { bean, itemView ->
                    run{
                        canSearch(bean.searchText!!)
                    }
                })
                (binding.recycleView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                adapter.setHasStableIds(true)
                adapter.setData(it)
                binding.recycleView.adapter = adapter
            }
        })
    }

}