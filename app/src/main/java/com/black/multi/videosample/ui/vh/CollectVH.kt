package com.black.multi.videosample.ui.vh

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemCollectBinding
import com.black.multi.videosample.databinding.ItemKnowledgeSubListBinding
import com.black.multi.videosample.model.CollectChapterData
import com.black.multi.videosample.model.DataX
import com.black.multi.videosample.model.KnowledgeSubList

/**
 * Created by wei.
 * Date: 2020/6/2 16:05
 * Desc:
 */
class CollectVH(owner: LifecycleOwner,
                adapter: BaseAdapter<CollectVH, CollectChapterData>,
                binding: ItemCollectBinding,
                callback: IRecycleViewCallback<CollectChapterData>) : XViewHolder<ItemCollectBinding, CollectChapterData>(owner,
        adapter,
        binding,
        callback) {
    override fun onBind(bean: CollectChapterData) {
        binding.bean = bean
    }

    override fun getCurrentT(): CollectChapterData? = binding.bean
}