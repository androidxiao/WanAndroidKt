package com.black.multi.videosample.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.black.multi.videosample.R
import com.black.multi.videosample.databinding.LayoutSettingViewBinding
import com.black.xcommon.utils.EzLog
import kotyox.layout.XConstraintLayout

/**
 * Created by wei.
 * Date: 2020/6/2 15:03
 * Desc:
 */
class ItemSettingView : XConstraintLayout {

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        initView(context, attributeSet)
    }

    private fun initView(context: Context, attributeSet: AttributeSet?) {
        val binding = DataBindingUtil.inflate<LayoutSettingViewBinding>(LayoutInflater.from(context), R.layout.layout_setting_view, this,true)
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.ItemSettingView)
        val title = ta.getString(R.styleable.ItemSettingView_title)
        ta.recycle()
        EzLog.d("title${title}")
        binding.tvTitle.text = title
    }
}