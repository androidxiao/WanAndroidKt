package com.black.multi.videosample.ui.adapter

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.SimpleBaseAdapter
import com.black.multi.videosample.model.ProjectListData
import com.black.multi.videosample.ui.vh.ProjectListVH

/**
 * Created by wei.
 * Date: 2020/5/28 9:48
 * Desc:
 */
class ProjectListAdapter(owner: LifecycleOwner, callback: IRecycleViewCallback<ProjectListData>) :SimpleBaseAdapter<ProjectListVH,ProjectListData>(owner,callback) {

    override fun getItemId(position: Int): Long {
        return datas[position].id.toLong()
    }
}