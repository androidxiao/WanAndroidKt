package com.black.multi.videosample.ui.adapter

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.base.baseadapter.SimpleBaseAdapter
import com.black.multi.videosample.model.RankScoreDataX
import com.black.multi.videosample.ui.vh.ScoreRankVH

/**
 * Created by wei.
 * Date: 2020/6/2 16:04
 * Desc:
 */
class ScoreRankAdapter(owner: LifecycleOwner, callback: IRecycleViewCallback<RankScoreDataX>) :SimpleBaseAdapter<ScoreRankVH, RankScoreDataX>(owner,callback) {

    override fun getItemId(position: Int): Long {
        return datas[position].userId.toLong()
    }
}