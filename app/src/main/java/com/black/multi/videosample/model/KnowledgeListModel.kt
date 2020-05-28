package com.black.multi.videosample.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by wei.
 * Date: 2020/5/28 9:38
 * Desc:
 */
data class KnowledgeListModel(
    val curPage: Int,
    val datas: List<KnowledgeSubList>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class KnowledgeSubList(
        val apkLink: String?,
        val audit: Int,
        val author: String?,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String?,
        val collect: Boolean,
        val courseId: Int,
        val desc: String?,
        val descMd: String?,
        val envelopePic: String?,
        val fresh: Boolean,
        val id: Int,
        val link: String?,
        val niceDate: String?,
        val niceShareDate: String?,
        val origin: String?,
        val prefix: String?,
        val projectLink: String?,
        val publishTime: Long,
        val selfVisible: Int,
        val shareDate: Any,
        val shareUser: String?,
        val superChapterId: Int,
        val superChapterName: String?,
        val tags: List<Any>,
        val title: String?,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
)