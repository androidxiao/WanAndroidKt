package com.black.multi.videosample.ui.adapter

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.SimpleBaseAdapter
import com.black.multi.videosample.model.DataX
import com.black.multi.videosample.ui.vh.HomeVH

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class HomeAdapter(owner: LifecycleOwner, callback: IRecycleViewCallback<DataX>) :SimpleBaseAdapter<HomeVH,DataX>(owner,callback) {

    override fun getItemId(position: Int): Long {
        return datas[position].id.toLong()
    }
}