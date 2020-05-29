package com.black.multi.videosample.ui.adapter

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.SimpleBaseAdapter
import com.black.multi.videosample.ui.vh.NavTitleVH

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class NavTitleAdapter(owner: LifecycleOwner, callback: IRecycleViewCallback<String>) :SimpleBaseAdapter<NavTitleVH,String>(owner,callback) {

    override fun getItemId(position: Int): Long {
        return datas[position].hashCode().toLong()
    }
}