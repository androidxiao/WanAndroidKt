package com.black.multi.videosample.ui.vh

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemCollectBinding
import com.black.multi.videosample.model.CollectChapterData
import com.black.multi.videosample.viewmodel.CollectVM
import com.black.xcommon.utils.EzLog

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
        binding.ivCollect.isSelected = true
        binding.ivCollect.setOnClickListener {
            CollectVM.instance.unCollectChapter(bean.id,bean.originId).observe(owner, Observer {
                when (it.status) {
                    Status.LOADING ->{

                    }
                    Status.SUCCESS ->{
                        adapter.remove(bean)
                    }
                    Status.ERROR ->{
                        EzLog.d("UnCollect Chapter is Error ${it.msg}")
                    }
                }
            })
        }
    }

    override fun getCurrentT(): CollectChapterData? = binding.bean
}