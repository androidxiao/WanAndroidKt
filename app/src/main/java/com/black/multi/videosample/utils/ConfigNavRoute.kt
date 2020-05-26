package com.black.multi.videosample.utils

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.black.multi.videosample.R

/**
 * Created by wei.
 * Date: 2020/5/26 16:59
 * Desc:
 */
object ConfigNavRoute {

    private lateinit var navController: NavController

    fun configNavRoute(activity:FragmentActivity):NavController {
        val navHostFg = activity.supportFragmentManager.findFragmentById(R.id.nav_config_route)
        navHostFg?.let { navController = NavHostFragment.findNavController(it) }
        NavGraphBuilder.build(activity, navController, navHostFg!!.id)
        return navController
    }


}