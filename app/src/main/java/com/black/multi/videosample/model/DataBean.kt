package com.black.multi.videosample.model

/**
 * Created by wei.
 * Date: 2020/5/25 16:00
 * Desc:
 */

//轮播图
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

//bottomNavigation
data class BottomBar(
        var activeColor: String,
        var inActiveColor: String,
        var selectTab: Int = 0,
        var tabs: List<Tab>
)
//bottomNavigation
data class Tab(
        var enable: Boolean,
        var index: Int,
        var pageUrl: String,
        var size: Int,
        var tintColor: String,
        var title: String
)

//navigation bean
data class Destination(
        var asStartPage: Boolean,
        var clazzName: String,
        var id: Int,
        var isFragment: Boolean,
        var needLogin: Boolean,
        var pageUrl: String
)

//------------------------------------------------------------首页列表
data class HomeModel(
        val curPage: Int,
        val datas: List<DataX>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
)

data class DataX(
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
)

data class Tag(
        val name: String,
        val url: String
)

//----------------------------------------知识体系列表-------------------
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
//----------------------------------------------------------------知识体系
data class KnowledgeModel(
        val children: List<Children>,
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
)

data class Children(
        val children: List<Any>,
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
)

//-------------------------------------------------------------登录
data class LoginModel(
        val chapterTops: MutableList<String>,
        val collectIds: MutableList<String>,
        val email: String,
        val icon: String,
        val id: Int,
        val password: String,
        val token: String,
        val type: Int,
        val username: String
)

//--------------------------------------------------------------导航数据
data class NavModel(
        val articles: List<Article>,
        val cid: Int,
        val name: String
)

data class Article(
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val selfVisible: Int,
        val shareDate: Any,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Any>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
)

//-----------------------------------------------------------项目列表
data class ProjectListModel(
        val curPage: Int,
        val datas: List<ProjectListData>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
)

data class ProjectListData(
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
)

data class ProjectListTag(
        val name: String,
        val url: String
)

//----------------------------------------------------------------项目标题
data class ProjectTitleModel(val courseId: Int = 0){
    var children: List<Any>?=null
    //    val courseId: Int = 0
    val id: Int = 0
    var name: String?=null
        get() = field?.replace("&amp;","")
    val order: Int=0
    val parentChapterId: Int=0
    var userControlSetTop: Boolean = false
    var visible: Int = 0
}

//--------------------------------------------------------------收藏列表
data class CollectChapterModel(
    val curPage: Int,
    val datas: List<CollectChapterData>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class CollectChapterData(
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val originId: Int,
    val publishTime: Long,
    val title: String,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

//-------------------------------------------个人积分
data class PersonalScoreModel(
    val coinCount: Int,
    val rank: Int,
    val userId: Int,
    val username: String
)