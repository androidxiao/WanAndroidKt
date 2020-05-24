package com.black.multi.videosample.model

/**
 * Created by wei.
 * Date: 2020/5/24 下午10:36
 * Description:
 */


data class Destination(
    var asStartPage: Boolean,
    var clazzName: String,
    var id: Int,
    var isFragment: Boolean,
    var needLogin: Boolean,
    var pageUrl: String
)
