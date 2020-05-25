package com.black.multi.videosample.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MenuItem
import com.black.multi.videosample.R
import com.black.multi.videosample.model.BottomBar
import com.black.multi.videosample.model.Destination
import com.black.multi.videosample.model.Tab
import com.black.multi.videosample.utils.AppConfig
import com.black.multi.videosample.utils.AppGlobals
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotyoxutils.Px2DpUtil

/**
 * Created by wei.
 * Date: 2020/5/25 9:38
 * Desc:
 */

class MainBottomBar : BottomNavigationView {


    private val sIcons = intArrayOf(R.drawable.ic_home, R.drawable.ic_knowledge, R.drawable.ic_project, R.drawable.ic_nav, R.drawable.ic_person)
    private var config: BottomBar? = null

    constructor(context: Context) : super(context)
    @SuppressLint("RestrictedApi")
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        config = AppConfig.getBottomBarConfig()
        if (config != null) {
            val state = arrayOfNulls<IntArray>(2)
            state[0] = intArrayOf(android.R.attr.state_selected)
            state[1] = intArrayOf()
            val colors = intArrayOf(Color.parseColor(config!!.activeColor), Color.parseColor(config!!.inActiveColor))
            val stateList = ColorStateList(state, colors)
            itemTextColor = stateList
            itemIconTintList = stateList
            //LABEL_VISIBILITY_LABELED:设置按钮的文本为一直显示模式
            //LABEL_VISIBILITY_AUTO:当按钮个数小于三个时一直显示，或者当按钮个数大于3个且小于5个时，被选中的那个按钮文本才会显示
            //LABEL_VISIBILITY_SELECTED：只有被选中的那个按钮的文本才会显示
            //LABEL_VISIBILITY_UNLABELED:所有的按钮文本都不显示
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

            val tabs: List<Tab> = config!!.tabs
            for (tab in tabs) {
                if (!tab.enable) {
                    continue
                }
                val itemId = getItemId(tab.pageUrl)
                if (itemId < 0) {
                    continue
                }
                val menuItem: MenuItem = menu.add(0, itemId, tab.index, tab.title)
                menuItem.setIcon(sIcons[tab.index])
            }

            //此处给按钮icon设置大小
            var index = 0
            for (tab in config!!.tabs) {
                if (!tab.enable) {
                    continue
                }
                val itemId = getItemId(tab.pageUrl)
                if (itemId < 0) {
                    continue
                }
                val iconSize = Px2DpUtil.dp2px(AppGlobals.getApplication(), tab.size.toFloat())
                val menuView = getChildAt(0) as BottomNavigationMenuView
                val itemView = menuView.getChildAt(index) as BottomNavigationItemView
                itemView.setIconSize(iconSize)
                if (TextUtils.isEmpty(tab.title)) {
                    val tintColor = if (TextUtils.isEmpty(tab.tintColor)) Color.parseColor("#000000") else Color.parseColor(tab.tintColor)
                    itemView.setIconTintList(ColorStateList.valueOf(tintColor))
                    //禁止掉点按时 上下浮动的效果
                    itemView.setShifting(false)
                    /**
                     * 如果想要禁止掉所有按钮的点击浮动效果。
                     * 那么还需要给选中和未选中的按钮配置一样大小的字号。
                     *
                     * 在MainActivity布局的AppBottomBar标签增加如下配置，
                     * @style/active，@style/inActive 在style.xml中
                     * app:itemTextAppearanceActive="@style/active"
                     * app:itemTextAppearanceInactive="@style/inActive"
                     */
                }
                index++
            }

            //底部导航栏默认选中项
            if (config!!.selectTab !== 0) {
                val selectTab: Tab = config!!.tabs[config!!.selectTab]
                if (selectTab.enable) {
                    val itemId = getItemId(selectTab.pageUrl)
                    //这里需要延迟一下 再定位到默认选中的tab
                    //因为 咱们需要等待内容区域,也就NavGraphBuilder解析数据并初始化完成，
                    //否则会出现 底部按钮切换过去了，但内容区域还没切换过去
                    post { selectedItemId = itemId }
                }
            }
        }
    }

    private fun getItemId(pageUrl: String): Int {
        val destination: Destination = AppConfig.getDestConfig()?.get(pageUrl) ?: return -1
        return destination.id
    }
}