package com.black.multi.videosample.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.black.multi.videosample.R
import com.black.multi.videosample.base.ui.BaseActivity
import com.black.multi.videosample.databinding.ActivityOtherBinding
import com.black.multi.videosample.utils.NavGraphBuilder

/**
 * Created by wei.
 * Date: 2020/5/27 9:44
 * Desc:
 */
class OtherActivity :BaseActivity<ActivityOtherBinding>() {

    private lateinit var navController: NavController

    override fun beforeInitView(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.activity_other

    override fun afterInitView(savedInstanceState: Bundle?) {
        initNavConfig()
    }

    private fun initNavConfig(){
        val navHostFg = supportFragmentManager.findFragmentById(R.id.nav_host_fg_other)
        navHostFg?.let { navController = NavHostFragment.findNavController(it) }
        NavGraphBuilder.build(this, navController, navHostFg!!.id)
    }
}