package com.black.multi.videosample.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.annotation.RequiresApi

/**
 * Created by wei.
 * Date: 2020/5/25 10:03
 * Desc:
 */
class WindowInsetsFrameLayout :FrameLayout{

    constructor(context: Context,attrs: AttributeSet?):super(context)
    constructor(context:Context,attrs:AttributeSet? =null,defStyleAttr:Int=0):super(context,attrs,defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun addView(child: View?) {
        super.addView(child)
        requestApplyInsets()
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun dispatchApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        var windowsInset = super.dispatchApplyWindowInsets(insets)
        if (!windowsInset.isConsumed) {
            for (i in 0 until childCount) {
                windowsInset = getChildAt(i).dispatchApplyWindowInsets(insets)
            }
        }
        return windowsInset
    }
}