package com.black.multi.videosample.ui

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.databinding.ItemRvBinding
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder

/**
 * Created by wei.
 * Date: 2020/5/24 下午6:18
 * Description:
 */
class XHolder(
    owner: LifecycleOwner,
    adapter: BaseAdapter<*, *>,
    binding: ItemRvBinding,
    callback: IRecycleViewCallback<String>
) : XViewHolder<ItemRvBinding, String>(
    owner,
    adapter,
    binding,
    callback
) {
    override fun onBind(var1: String) {
        binding.tv.text =var1
    }
    override fun getCurrentT(): String? {
        return binding.bean
    }
}