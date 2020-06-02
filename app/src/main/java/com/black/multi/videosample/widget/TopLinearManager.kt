package com.black.multi.videosample.widget

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

/**
 * Created by wei.
 * Date: 2020/5/29 15:35
 * Desc:
 */
class TopLinearManager : LinearSmoothScroller {
    constructor(context: Context):super(context)

    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }
}