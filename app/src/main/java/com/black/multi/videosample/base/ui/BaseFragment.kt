package com.black.multi.videosample.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.black.multi.videosample.utils.AppConfig
import com.black.multi.videosample.utils.LOGIN_IN_PAGE
import com.black.multi.videosample.utils.Login_First
import com.black.multi.videosample.utils.ShowHideBottomBar
import com.black.xcommon.tips.DefaultTipsHelper
import com.black.xcommon.tips.IBaseView
import com.black.xcommon.tips.TipsHelper
import com.black.xcommon.utils.EzLog
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * Created by wei.
 * Date: 2020/5/24 下午7:40
 * Description:
 */
abstract class BaseFragment<B : ViewDataBinding> :Fragment(), IBaseView, View.OnClickListener {

    protected lateinit var binding: B
    protected var ivBack:ImageView?=null
    private var mTipsHelper: TipsHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        beforeInitView(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater!!, getLayoutId(), container, false)
        observableIsLogin()
        initView(savedInstanceState)
        val initListener = initListener()
        if (initListener != null) {
            for(i in initListener)
            binding.root.findViewById<View>(i).setOnClickListener(this)
        }
        navigationUp()
        afterInitView(savedInstanceState)
        return binding.root
    }

    protected open fun initListener():Array<Int>?{
        return null
    }


    /**
     * 监听是否登录
     */
    private fun observableIsLogin(){
        LiveEventBus.get(Login_First).observe(this, Observer {
            toLogin()
        })
    }

    private fun toLogin(){
        val navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
        navigate(LOGIN_IN_PAGE,navOptions = navOptions)
    }

    private fun navigationUp(){
        if (ivBack != null) {
            ivBack?.setOnClickListener {
                val id = ShowHideBottomBar.instance.getId()
                val isMain = ShowHideBottomBar.instance.getIsMain()
                EzLog.d("navigationUp--finish--to--page--id--->${ShowHideBottomBar.instance.getId()}----isMain-->${isMain}")
                if (id != null && isMain != null && isMain) {
                    fgPopBack()
                }
            }
        }
    }

    /**
     * 弹出栈中的 fg
     */
    protected fun fgPopBack(){
        NavHostFragment.findNavController(this).popBackStack()
    }

    //-------------------------------------------------------------------
    /**
     * 根据 pageUrl 跳转到具体的页面
     */
    protected fun navigateToNextPage(){
        val destination = AppConfig.getDestConfig()!![nextPageUrl()]
        NavHostFragment.findNavController(this).navigate(destination!!.id,getBundle(),navigateAnimation())
    }

    /**
     * 跳转的具体页面
     */
    protected open fun nextPageUrl():String?{
        return null
    }

    /**
     * 跳转时携带的参数
     */
    protected open fun getBundle():Bundle?{
        return null
    }

    /**
     * 跳转动画
     */
    protected open fun navigateAnimation():NavOptions?{
        return null
    }

    //-------------------------------------------------------------------

    /**
     * 添加参数，直接跳转
     */
    protected open fun navigate(destination:String,bundle: Bundle?=null,navOptions: NavOptions?=null) {
        val destination = AppConfig.getDestConfig()!![destination]
        NavHostFragment.findNavController(this).navigate(destination!!.id,bundle,navOptions)
    }

    open fun setTipView(view: View?) {
        if (mTipsHelper == null) mTipsHelper = DefaultTipsHelper(context, view)
    }

    /**
     * 需要在数据回调的地方手动调用
     */
    override fun onRefreshEmpty() {
        if (mTipsHelper != null) {
            mTipsHelper!!.showEmpty()
        }
    }

    override fun onRefreshFailure(message: String?) {
        if (mTipsHelper!=null) {
            mTipsHelper!!.hideEmpty()
            mTipsHelper!!.hideLoading()
            mTipsHelper!!.showError(true, message) { v -> onRetryBtnClick() }
        }
    }

    override fun showLoading() {
        if (mTipsHelper != null) {
            mTipsHelper!!.showLoading(true)
        }
    }

    override fun showContent() {
        if (mTipsHelper != null) {
            mTipsHelper!!.hideEmpty()
            mTipsHelper!!.hideError()
            mTipsHelper!!.hideLoading()
        }
    }

    protected open fun onRetryBtnClick() {}

    abstract fun beforeInitView(bundle: Bundle?)

    abstract fun initView(bundle: Bundle?)

    abstract fun afterInitView(bundle: Bundle?)

    protected abstract fun getLayoutId(): Int

    override fun onClick(v: View) {
    }
}