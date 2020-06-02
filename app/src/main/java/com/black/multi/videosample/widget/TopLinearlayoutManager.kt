package com.black.multi.videosample.widget

import android.content.Context
import android.graphics.PointF
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by wei.
 * Date: 2020/5/29 17:12
 * Desc:
 */
class TopLinearlayoutManager : LinearLayoutManager {

    constructor(context: Context):super(context)

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        val linearSmoothScroller: LinearSmoothScroller = object : LinearSmoothScroller(recyclerView.context) {
            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return this@TopLinearlayoutManager.computeScrollVectorForPosition(targetPosition)
            }

            override fun calculateTimeForScrolling(dx: Int): Int {
                var dx = dx
                if (dx > 3000) {
                    dx = 3000
                }
                return super.calculateTimeForScrolling(dx)
            }
        }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }
}