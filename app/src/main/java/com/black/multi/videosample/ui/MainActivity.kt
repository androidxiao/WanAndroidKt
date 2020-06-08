package com.black.multi.videosample.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.black.multi.aoputils.annotation.Login_Out
import com.black.multi.aoputils.utils.UserManager
import com.black.multi.videosample.R
import com.black.multi.videosample.base.ui.BaseActivity
import com.black.multi.videosample.databinding.ActivityMainBinding
import com.black.multi.videosample.utils.*
import com.black.xcommon.utils.EzLog
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity<ActivityMainBinding>(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController

    override fun beforeInitView(savedInstanceState: Bundle?) {
        observableIsLoginByAspect()
    }

    private fun observableIsLoginByAspect() {
        UserManager.instance.setIsLoginListener { loginType ->
            if (loginType == Login_Out) {
                mBinding.mainBottomBar.apply {
                    selectedItemId = getItemId(HOME_PAGE)
                }
            } else {
                navigate(LOGIN_IN_PAGE)
            }
        }
    }

    private fun navigate(pageUrl:String){
        val destination = AppConfig.getDestConfig()!![pageUrl]
        navController.navigate(destination!!.id)
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initNavConfig()
        initTitle()
        clickSearch()
    }


    private fun clickSearch() {

        mBinding.includeToolbar.tvRight.setOnClickListener {
            navigate(SEARCH_KEY_PAGE)
        }
    }


    private fun initNavConfig() {
        val navHostFg = supportFragmentManager.findFragmentById(R.id.nav_host_fg)
        navHostFg?.let { navController = NavHostFragment.findNavController(it) }
        NavGraphBuilder.build(this, navController, navHostFg!!.id)
        mBinding.mainBottomBar.setOnNavigationItemSelectedListener(this)

        ShowHideBottomBar.instance.showHideBottomBar(
            navController,
            mBinding.includeToolbar.toolbar,
            mBinding.mainBottomBar
        )
    }

    override fun afterInitView(savedInstanceState: Bundle?) {

    }

    private fun initTitle() {
        mBinding.includeToolbar.titleTv.text = AppConfig.getBottomBarFirstTitle()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val destConfig = AppConfig.getDestConfig()
        val iterator = destConfig!!.entries.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            var destination = next.value
            if (destination != null && !UserManager.instance.isLogin() && destination.needLogin && destination.id == item.itemId) {
                mBinding.mainBottomBar.selectedItemId = item.itemId
                return false
            }
        }
        navController.navigate(item.itemId)
        mBinding.includeToolbar.titleTv.text = if (TextUtils.isEmpty(item.title)) "项目" else {
            item.title
        }
        //        return !TextUtils.isEmpty(item.title)
        return true
    }

    override fun onBackPressed() {
//        super.onBackPressed()

        val id = ShowHideBottomBar.instance.getId()
        val isMain = ShowHideBottomBar.instance.getIsMain()
        EzLog.d("onBackPressed--finish--to--page--id--->${ShowHideBottomBar.instance.getId()}----isMain-->${isMain}")
        if (id != null && isMain != null && isMain) {
//            navController.navigateUp()
            //NavController 有 navigateUp() 和 popBackStack() 都可以返回上一级，
            // 区别:
            //popBackStack() 如果当前的返回栈是空的就会报错，因为栈是空的了
            // navigateUp() 则不会，还是停留在当前界面
            navController.popBackStack()
        } else {
            val curPageId = navController.currentDestination?.id
            //APP 页面路由导航图，获取首页的 destinationId
            val startDestinationId = navController.graph.startDestination
            //如果当前显示的不是首页，点击返回键时，则拦截
            if (curPageId != startDestinationId) {
                mBinding.mainBottomBar.selectedItemId = startDestinationId
                return
            }

            //直接 finish
            finish()
        }
    }

}
