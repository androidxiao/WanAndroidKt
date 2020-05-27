package com.black.multi.videosample.ui.vh

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemKnowledgeSubBinding
import com.black.multi.videosample.model.Children

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class KnowledgeSubVH(owner: LifecycleOwner,
                     adapter: BaseAdapter<KnowledgeSubVH, Children>,
                     binding: ItemKnowledgeSubBinding,
                     callback: IRecycleViewCallback<Children>) : XViewHolder<ItemKnowledgeSubBinding, Children>(owner,
        adapter,
        binding,
        callback) {
    override fun onBind(bean: Children) {
        binding.bean = bean
    }

    override fun getCurrentT(): Children? = binding.bean
}