package com.black.multi.videosample.ui.vh

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemProjectBinding
import com.black.multi.videosample.model.ProjectListData

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class ProjectListVH(owner: LifecycleOwner,
                    adapter: BaseAdapter<ProjectListVH, ProjectListData>,
                    binding: ItemProjectBinding,
                    callback: IRecycleViewCallback<ProjectListData>) : XViewHolder<ItemProjectBinding, ProjectListData>(owner,
        adapter,
        binding,
        callback) {
    override fun onBind(bean: ProjectListData) {
        binding.bean = bean
    }

    override fun getCurrentT(): ProjectListData? = binding.bean
}