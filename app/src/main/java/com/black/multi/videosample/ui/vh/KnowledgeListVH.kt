package com.black.multi.videosample.ui.vh

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemHomeBinding
import com.black.multi.videosample.databinding.ItemKnowledgeSubBinding
import com.black.multi.videosample.databinding.ItemKnowledgeSubListBinding
import com.black.multi.videosample.model.DataX
import com.black.multi.videosample.model.KnowledgeSubList

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class KnowledgeListVH(owner: LifecycleOwner,
                      adapter: BaseAdapter<KnowledgeListVH, DataX>,
                      binding: ItemKnowledgeSubListBinding,
                      callback: IRecycleViewCallback<KnowledgeSubList>) : XViewHolder<ItemKnowledgeSubListBinding, KnowledgeSubList>(owner,
        adapter,
        binding,
        callback) {
    override fun onBind(bean: KnowledgeSubList) {
        binding.bean = bean
    }

    override fun getCurrentT(): KnowledgeSubList? = binding.bean
}