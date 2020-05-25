package com.black.multi.videosample.model

/**
 * Created by wei.
 * Date: 2020/5/25 16:00
 * Desc:
 */
data class Banner(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)