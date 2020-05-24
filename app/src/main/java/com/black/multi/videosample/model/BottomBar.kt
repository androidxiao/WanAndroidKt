package com.black.multi.videosample.model

/**
 * Created by wei.
 * Date: 2020/5/24 下午11:00
 * Description:
 */
data class BottomBar(
    var activeColor: String,
    var inActiveColor: String,
    var selectTab: Int = 0,
    var tabs: List<Tab>
)

data class Tab(
    var enable: Boolean,
    var index: Int,
    var pageUrl: String,
    var size: Int,
    var tintColor: String,
    var title: String
)