package com.black.multi.videosample.ui.adapter

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.SimpleBaseAdapter
import com.black.multi.videosample.model.DataX
import com.black.multi.videosample.model.KnowledgeSubList
import com.black.multi.videosample.ui.vh.HomeVH
import com.black.multi.videosample.ui.vh.KnowledgeListVH

/**
 * Created by wei.
 * Date: 2020/5/28 9:48
 * Desc:
 */
class KnowledgeListAdapter(owner: LifecycleOwner, callback: IRecycleViewCallback<KnowledgeSubList>) :SimpleBaseAdapter<KnowledgeListVH,KnowledgeSubList>(owner,callback) {

    override fun getItemId(position: Int): Long {
        return datas[position].id.toLong()
    }
}