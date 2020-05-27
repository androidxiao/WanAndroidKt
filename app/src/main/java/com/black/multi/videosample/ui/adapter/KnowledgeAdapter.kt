package com.black.multi.videosample.ui.adapter

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.SimpleBaseAdapter
import com.black.multi.videosample.model.KnowledgeModel
import com.black.multi.videosample.ui.vh.KnowledgeVH

/**
 * Created by wei.
 * Date: 2020/5/27 下午9:05
 * Description:
 */
class KnowledgeAdapter(owner: LifecycleOwner, callback: IRecycleViewCallback<KnowledgeModel>) :SimpleBaseAdapter<KnowledgeVH,KnowledgeModel>(owner,callback) {

    override fun getItemId(position: Int): Long {
        return datas[position].id.toLong()
    }
}