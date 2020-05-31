package com.black.multi.videosample.ui.vh

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemNavListBinding
import com.black.multi.videosample.fragment.KnowledgeListFragment_Title
import com.black.multi.videosample.model.Article
import com.black.multi.videosample.model.NavModel
import com.black.multi.videosample.ui.adapter.NavSubContentAdapter
import com.black.multi.videosample.utils.AppConfig
import com.black.multi.videosample.utils.HOME_DETAIL_PAGE
import com.black.multi.videosample.utils.HomeDetailFragment_Url
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class NavContentVH(owner: LifecycleOwner,
                   adapter: BaseAdapter<NavContentVH, NavModel>,
                   binding: ItemNavListBinding,
                   callback: IRecycleViewCallback<NavModel>) : XViewHolder<ItemNavListBinding, NavModel>(owner,
        adapter,
        binding,
        callback) {

    init {
        val manager = FlexboxLayoutManager(binding.subRv.context)
        //设置主轴排列方式
        manager.flexDirection = FlexDirection.ROW
        //设置是否换行
        manager.flexWrap = FlexWrap.WRAP
        manager.alignItems = AlignItems.STRETCH
        binding.subRv.layoutManager = manager
    }

    override fun onBind(bean: NavModel) {
        binding.bean = bean
        val mAdapter = NavSubContentAdapter(owner, IRecycleViewCallback<Article> { bean, itemView ->
            run{
                val destination = AppConfig.getDestConfig()!![HOME_DETAIL_PAGE]
                val bundle = Bundle()
                bundle.putString(HomeDetailFragment_Url,bean.link)
                bundle.putString(KnowledgeListFragment_Title,bean.title)
                NavHostFragment.findNavController(owner as Fragment).navigate(destination!!.id,bundle)
            }
        })
        mAdapter.setData(bean.articles)
        binding.subRv.adapter = mAdapter
    }

    open fun setChoose(){

    }

    override fun getCurrentT(): NavModel? = binding.bean
}