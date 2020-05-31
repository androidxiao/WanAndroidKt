package com.black.multi.videosample.ui.vh

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemSubContentNavBinding
import com.black.multi.videosample.model.Article

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class NavSubContentVH(owner: LifecycleOwner,
                      adapter: BaseAdapter<NavSubContentVH, Article>,
                      binding: ItemSubContentNavBinding,
                      callback: IRecycleViewCallback<Article>) : XViewHolder<ItemSubContentNavBinding, Article>(owner,
        adapter,
        binding,
        callback) {
    override fun onBind(bean: Article) {
        binding.bean = bean
    }

    override fun getCurrentT(): Article? = binding.bean
}