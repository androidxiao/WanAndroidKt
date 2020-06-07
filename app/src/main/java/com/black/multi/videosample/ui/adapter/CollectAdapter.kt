package com.black.multi.videosample.ui.adapter

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.SimpleBaseAdapter
import com.black.multi.videosample.model.CollectChapterData
import com.black.multi.videosample.ui.vh.CollectVH

/**
 * Created by wei.
 * Date: 2020/6/2 16:04
 * Desc:
 */
class CollectAdapter(owner: LifecycleOwner, callback: IRecycleViewCallback<CollectChapterData>) :SimpleBaseAdapter<CollectVH,CollectChapterData>(owner,callback) {

    override fun getItemId(position: Int): Long {
        return datas[position].id.toLong()
    }
}