package com.black.multi.videosample.ui.vh

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemNavListBinding
import com.black.multi.videosample.databinding.ItemNavTitleBinding
import com.black.multi.videosample.model.Article
import com.black.xcommon.utils.EzLog

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class NavContentVH(owner: LifecycleOwner,
                   adapter: BaseAdapter<NavContentVH, Article>,
                   binding: ItemNavListBinding,
                   callback: IRecycleViewCallback<Article>) : XViewHolder<ItemNavListBinding, Article>(owner,
        adapter,
        binding,
        callback) {
    override fun onBind(bean: Article) {
        binding.bean = bean
    }

    override fun getCurrentT(): Article? = binding.bean
}