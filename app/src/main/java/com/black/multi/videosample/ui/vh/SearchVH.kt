package com.black.multi.videosample.ui.vh

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemSearchHistoryBinding
import com.black.multi.videosample.model.database.SearchEntity
import com.black.multi.videosample.viewmodel.SearchDaoVM

/**
 * Created by wei.
 * Date: 2020/6/2 16:05
 * Desc:
 */
class SearchVH(owner: LifecycleOwner,
               adapter: BaseAdapter<SearchVH, SearchEntity>,
               binding: ItemSearchHistoryBinding,
               callback: IRecycleViewCallback<SearchEntity>) : XViewHolder<ItemSearchHistoryBinding, SearchEntity>(owner,
        adapter,
        binding,
        callback) {
    override fun onBind(bean: SearchEntity) {
        binding.bean = bean
        binding.ivDelete.setOnClickListener {
            SearchDaoVM.instance.deleteSearchText(bean).observe(owner, Observer {
                if(it>0){
                    adapter.remove(bean)
                }
            })
        }
    }

    override fun getCurrentT(): SearchEntity? = binding.bean
}