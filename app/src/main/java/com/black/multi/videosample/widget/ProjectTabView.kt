package com.black.multi.videosample.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.black.multi.videosample.R
import com.black.multi.videosample.databinding.ProjectTabViewBinding
import com.black.multi.videosample.fragment.ProjectListFragment
import com.black.multi.videosample.model.ProjectTitleModel
import com.black.xcommon.utils.EzLog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Created by wei.
 * Date: 2020/5/28 16:04
 * Desc:
 */
class ProjectTabView : ConstraintLayout {

    private lateinit var mBind: ProjectTabViewBinding
    private lateinit var mTitles: ArrayList<ProjectTitleModel>
    private lateinit var mTabMediator: TabLayoutMediator

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    open fun initView(titles: List<ProjectTitleModel>?, activity: Fragment) {
        mTitles = titles as ArrayList<ProjectTitleModel>
        mBind = DataBindingUtil.inflate<ProjectTabViewBinding>(LayoutInflater.from(context), R.layout.project_tab_view, this, true)
        //禁止预加载
        mBind.viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        EzLog.d("${mTitles.size}")
        //viewPager2默认只有一种类型的Adapter。FragmentStateAdapter
        //并且在页面切换的时候 不会调用子Fragment的setUserVisibleHint，取而代之的是onPause(),onResume()
        mBind.viewPager.adapter = object : FragmentStateAdapter(activity.childFragmentManager, activity as Lifecycle) {
            override fun getItemCount(): Int = mTitles.size

            override fun createFragment(position: Int): Fragment {
                //FragmentStateAdapter内部自己会管理已实例化的fragment对象
                EzLog.d("aaaa")
                return ProjectListFragment.getInstance(titles[position].id)
            }
        }

        mBind.tabLayout.tabGravity = 0
        //viewPager2 不再用 TabLayout.setUpWithViewPager() 了
        //取而代之的是 TabLayoutMediator，我们可以在 onConfigureTab() 方法的回调里面 做 tab 标签的配置
        mTabMediator = TabLayoutMediator(mBind.tabLayout, mBind.viewPager, true, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            EzLog.d("bbbbbb")
            tab.customView = makeTabView(position)
        })
        mTabMediator.attach()
        mBind.viewPager.registerOnPageChangeCallback(mPageChangeCallback)
        //如果需要切换到默认选项，使用 post，因为，要等到 viewpager2 加载完成才行
//        mBind.viewPager.post {
//            mBind.viewPager.setCurrentItem(1, false)
//        }
    }

    private val mPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            val tabCount: Int = mBind.tabLayout.tabCount
            for (i in 0 until tabCount) {
                val tab: TabLayout.Tab? = mBind.tabLayout.getTabAt(i)
                val customView = tab!!.customView as TextView
                EzLog.d("ccccc")
                if (tab.position == position) {
                    customView.textSize = resources.getDimension(R.dimen.font_32px)
                    customView.typeface = Typeface.DEFAULT_BOLD
                } else {
                    customView.textSize = resources.getDimension(R.dimen.font_28px)
                    customView.typeface = Typeface.DEFAULT
                }
            }
        }
    }

    private fun makeTabView(position: Int): View? {
        val tabView = TextView(context)
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_selected)
        states[1] = intArrayOf()
        val colors = intArrayOf(R.color.colorAccent, R.color.c_222222)
        val stateList = ColorStateList(states, colors)
        tabView.setTextColor(stateList)
        tabView.text = mTitles[position].name
        tabView.textSize = resources.getDimension(R.dimen.font_28px)
        return tabView
    }

    override fun onDetachedFromWindow() {
        mTabMediator.detach()
        mBind.viewPager.unregisterOnPageChangeCallback(mPageChangeCallback)
        super.onDetachedFromWindow()
    }

}