package com.black.multi.videosample.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

/**
 * Created by wei.
 * Date: 2020/5/25 10:02
 * Desc:
 */
class WindowInsetsNavHostFragment :NavHostFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = WindowInsetsFrameLayout(inflater.context)
        layout.id =id
        return layout
    }
}