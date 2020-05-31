package com.black.multi.videosample.ui.adapter

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.SimpleBaseAdapter
import com.black.multi.videosample.model.NavModel
import com.black.multi.videosample.ui.vh.NavContentVH

/**
 * Created by wei.
 * Date: 2020/5/30 10:32
 * Desc:
 */
class NavContentAdapter(owner: LifecycleOwner, callback: IRecycleViewCallback<NavModel>) :SimpleBaseAdapter<NavContentVH, NavModel>(owner,callback) {

    override fun getItemId(position: Int): Long {
        return datas[position].cid.toLong()
    }

    override fun getCurPosition():Int{
        return curPosition
    }
}