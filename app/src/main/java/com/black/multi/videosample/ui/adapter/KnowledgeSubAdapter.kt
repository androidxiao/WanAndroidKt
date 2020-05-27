package com.black.multi.videosample.ui.adapter

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.SimpleBaseAdapter
import com.black.multi.videosample.model.Children
import com.black.multi.videosample.ui.vh.KnowledgeSubVH

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class KnowledgeSubAdapter(owner: LifecycleOwner, callback: IRecycleViewCallback<Children>) :SimpleBaseAdapter<KnowledgeSubVH,Children>(owner,callback) {

    override fun getItemId(position: Int): Long {
        return datas[position].id.toLong()
    }
}