package com.black.multi.videosample.ui.vh

import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.R
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemNavTitleBinding
import com.black.xcommon.utils.EzLog

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class NavTitleVH(owner: LifecycleOwner,
                 adapter: BaseAdapter<NavTitleVH, String>,
                 binding: ItemNavTitleBinding,
                 callback: IRecycleViewCallback<String>) : XViewHolder<ItemNavTitleBinding, String>(owner,
        adapter,
        binding,
        callback) {
    override fun onBind(bean: String) {
        binding.bean = bean
    }

    override fun getCurrentT(): String? = binding.bean

    open fun setChoose(position:Int){

        EzLog.d("position--->${position}---->adapterPosition--->${adapterPosition}--->layoutPosition-->${getmCurPosition()}")
        if(adapterPosition==position) {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorPrimary
                )
            )
        }else{
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.c_transparent
                )
            )
        }
    }
}