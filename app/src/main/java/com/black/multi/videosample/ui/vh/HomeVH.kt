package com.black.multi.videosample.ui.vh

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemHomeBinding
import com.black.multi.videosample.model.DataX
import com.black.multi.videosample.utils.toast
import com.black.multi.videosample.viewmodel.CollectVM
import com.black.xcommon.utils.EzLog

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class HomeVH(owner: LifecycleOwner,
             adapter: BaseAdapter<HomeVH, DataX>,
             binding: ItemHomeBinding,
             callback: IRecycleViewCallback<DataX>) : XViewHolder<ItemHomeBinding, DataX>(owner,
        adapter,
        binding,
        callback) {
    override fun onBind(bean: DataX) {
        binding.bean = bean
        binding.ivCollect.isSelected = bean.collect
        binding.ivCollect.setOnClickListener {
            CollectVM.instance.collectChapter(bean.id).observe(owner, Observer {
                when (it.status) {
                    Status.LOADING ->{

                    }
                    Status.SUCCESS ->{
                        binding.ivCollect.context.toast("收藏成功")
                        binding.ivCollect.isSelected = true
                    }
                    Status.ERROR ->{
                        EzLog.d("Collect Chapter is Error ${it.msg}")
                    }
                }
            })
        }
    }

    override fun getCurrentT(): DataX? = binding.bean
}