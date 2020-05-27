package com.black.multi.videosample.ui.vh

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemKnowledgeBinding
import com.black.multi.videosample.model.Children
import com.black.multi.videosample.model.KnowledgeModel
import com.black.multi.videosample.ui.adapter.KnowledgeSubAdapter
import com.black.xcommon.utils.EzLog
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * Created by wei.
 * Date: 2020/5/27 下午9:01
 * Description:
 */
class KnowledgeVH(owner: LifecycleOwner,
                  adapter: BaseAdapter<KnowledgeVH, KnowledgeModel>,
                  binding: ItemKnowledgeBinding,
                  callback: IRecycleViewCallback<KnowledgeModel>) : XViewHolder<ItemKnowledgeBinding, KnowledgeModel>(owner,
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
    override fun onBind(bean: KnowledgeModel) {
        binding.bean = bean
        val children = bean.children
        val mAdapter = KnowledgeSubAdapter(owner, IRecycleViewCallback<Children> { bean, itemView ->
            EzLog.d("${bean.name}-->${bean.id}")
        })
        mAdapter.setData(children)
        binding.subRv.adapter = mAdapter
    }

    override fun getCurrentT(): KnowledgeModel? = binding.bean
}