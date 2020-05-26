package com.black.multi.videosample.ui.vh

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemHomeBinding
import com.black.multi.videosample.model.DataX

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
    }

    override fun getCurrentT(): DataX? = binding.bean
}