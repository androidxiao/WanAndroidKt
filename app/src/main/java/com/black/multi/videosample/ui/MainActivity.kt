package com.black.multi.videosample.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.black.multi.videosample.R
import com.black.multi.videosample.base.ui.BaseActivity
import com.black.multi.videosample.databinding.ActivityMainBinding
import com.black.multi.videosample.utils.AppConfig
import com.black.multi.videosample.utils.NavGraphBuilder
import com.black.multi.videosample.utils.ShowHideBottomBar
import com.black.multi.videosample.utils.UserManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding>(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController

//        val adapter = RvAdapter(this,IRecycleViewCallback<String> { var1, var2 ->
//            Toast.makeText(this,"aa",Toast.LENGTH_SHORT).show()
//        })
//        val list = arrayListOf("aa","bb","cc","dd") as List<String>
//        adapter.setData(list)
//        rv.adapter =adapter

    override fun beforeInitView(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initNavConfig()
        initTitle()
        var isLight = true
        mBinding.includeToolbar.tvRight.setOnClickListener {
            if (isLight) {
                isLight = false
                dimBackground(1.0f,0.5f)
            }else{
                isLight = true
                dimBackground(0.5f,1.0f)
            }
        }
    }



    private fun initNavConfig(){
        val navHostFg = supportFragmentManager.findFragmentById(R.id.nav_host_fg)
        navHostFg?.let { navController = NavHostFragment.findNavController(it) }
        NavGraphBuilder.build(this, navController, navHostFg!!.id)
        mBinding.mainBottomBar.setOnNavigationItemSelectedListener(this)

        ShowHideBottomBar.instance.showHideBottomBar(navController,mBinding.includeToolbar.toolbar,mBinding.mainBottomBar)
    }

    override fun afterInitView(savedInstanceState: Bundle?) {

    }

    private fun initTitle(){
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
        super.onBackPressed()
        val curPageId = navController.currentDestination?.id
        //APP 页面路由导航图，获取首页的 destinationId
        val startDestinationId = navController.graph.startDestination
        //如果当前显示的不是首页，点击返回键时，则拦截
        if (curPageId != startDestinationId) {
            mBinding.mainBottomBar.selectedItemId = startDestinationId
            return
        }

        //否则，直接 finish，此处不宜使用 onBackPressed，因为 navigation 会操作返回栈，切换到之前的页面
        finish()

    }

}
