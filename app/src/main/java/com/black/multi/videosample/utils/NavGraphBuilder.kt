package com.black.multi.videosample.utils

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import com.black.multi.videosample.widget.ShowHideFragmentNavigator
import com.black.xcommon.utils.AppGlobals

/**
 * Created by wei.
 * Date: 2020/5/25 10:24
 * Desc:
 */
object NavGraphBuilder {

    fun build(activity: FragmentActivity, controller: NavController, containerId: Int) {
        val navProvider = controller.navigatorProvider
        //NavGraphNavigator 也是页面路由的一种，它只为默认的展示页提供导航服务，但真正的跳转还是交给对应的 navigator 来完成
        val navGraph = NavGraph(NavGraphNavigator(navProvider))

        //var fragmentNavigation =navProvider.getNavigator(FragmentNavigator::class.java) as FragmentNavigator
        //fragment 的导航此处使用 ShowHideFragmentNavigator，底部切换时，使用 show/hide，而不是 replace
        val showHideFragmentNavigator = ShowHideFragmentNavigator(activity, activity.supportFragmentManager, containerId)
        navProvider.addNavigator(showHideFragmentNavigator)
        val activityNavigator = navProvider.getNavigator<ActivityNavigator>(ActivityNavigator::class.java)
        val destConfig = AppConfig.getDestConfig()
        val iterator = destConfig?.values?.iterator()
        while (iterator!!.hasNext()) {
            var destination = iterator.next()
            if (destination.isFragment) {
                var navDestination = showHideFragmentNavigator.createDestination()
                navDestination.className = destination.clazzName
                navDestination.id = destination.id
                navDestination.addDeepLink(destination.pageUrl)
                navGraph.addDestination(navDestination)
            } else {
                var acDestination = activityNavigator.createDestination()
                acDestination.id = destination.id
                acDestination.setComponentName(ComponentName(AppGlobals.getApplication()!!.packageName, destination.clazzName))
                acDestination.addDeepLink(destination.pageUrl)
                navGraph.addDestination(acDestination)
            }

            //给 App 页面的 NavGraph 一个默认的展示页
            if (destination.asStartPage) {
                navGraph.startDestination = destination.id
            }
        }
        controller.graph = navGraph
    }
}