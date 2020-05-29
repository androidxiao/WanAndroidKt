package com.black.multi.videosample.ui.adapter

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.SimpleBaseAdapter
import com.black.multi.videosample.model.Article
import com.black.multi.videosample.ui.vh.NavContentVH
import com.black.multi.videosample.ui.vh.NavTitleVH

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class NavContentAdapter(owner: LifecycleOwner, callback: IRecycleViewCallback<Article>) :SimpleBaseAdapter<NavContentVH, Article>(owner,callback) {

    override fun getItemId(position: Int): Long {
        return datas[position].id.toLong()
    }
}