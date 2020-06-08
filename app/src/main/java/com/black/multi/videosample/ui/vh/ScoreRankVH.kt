package com.black.multi.videosample.ui.vh

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.BaseAdapter
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.XViewHolder
import com.black.multi.videosample.databinding.ItemScoreRankBinding
import com.black.multi.videosample.model.RankScoreDataX

/**
 * Created by wei.
 * Date: 2020/6/2 16:05
 * Desc:
 */
class ScoreRankVH(owner: LifecycleOwner,
                  adapter: BaseAdapter<ScoreRankVH, RankScoreDataX>,
                  binding: ItemScoreRankBinding,
                  callback: IRecycleViewCallback<RankScoreDataX>) : XViewHolder<ItemScoreRankBinding, RankScoreDataX>(owner,
        adapter,
        binding,
        callback) {
    override fun onBind(bean: RankScoreDataX) {
        binding.bean = bean
    }

    override fun getCurrentT(): RankScoreDataX? = binding.bean
}