package com.black.multi.videosample.base.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import com.just.agentweb.*


/**
 * Created by wei.
 * Date: 2020/5/26 15:51
 * Desc:
 */
abstract class BaseAgentWebFragment<B : ViewDataBinding> : BaseFragment<B>() {

    protected var mAgentWeb: AgentWeb? = null
    private var mMiddleWareWebChrome: MiddlewareWebChromeBase? = null
    private var mMiddleWareWebClient: MiddlewareWebClientBase? = null
    private val mAgentWebUIController: AgentWebUIControllerImplBase? = null

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getAgentWebParent(), ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(getIndicatorColor(), getIndicatorHeight())
                .setWebView(getWebView())
                .setWebLayout(getWebLayout())
                .setAgentWebWebSettings(getAgentWebSettings())
                .setWebViewClient(getWebViewClient())
                .setPermissionInterceptor(getPermissionInterceptor())
                .setWebChromeClient(getWebChromeClient())
                .interceptUnkownUrl()
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setAgentWebUIController(getAgentWebUIController())
                .useMiddlewareWebChrome(getMiddleWareWebChrome())
                .useMiddlewareWebClient(getMiddleWareWebClient())
                .createAgentWeb() //
                .ready() //
                .go(getUrl())
    }

    protected fun setTitle(view: WebView?, title: String?) {}

    override fun onPause() {
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onPause()
        }
        super.onPause()
    }

    override fun onResume() {
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onResume()
        }
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


    open  fun getUrl(): String? {
        return ""
    }

    override fun onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onDestroy()
        }
        super.onDestroy()
    }

    protected fun getAgentWebSettings(): IAgentWebSettings<*>? {
        return AgentWebSettingsImpl.getInstance()
    }


    protected fun getAgentWebUIController(): AgentWebUIControllerImplBase? {
        return mAgentWebUIController
    }

    protected fun getWebChromeClient(): WebChromeClient? {
        return null
    }

    @NonNull
    protected abstract fun getAgentWebParent(): ViewGroup

    @ColorInt
    protected fun getIndicatorColor(): Int {
        return -1
    }

    protected fun getIndicatorHeight(): Int {
        return -1
    }

    protected fun getWebViewClient(): WebViewClient? {
        return null
    }

    protected fun getWebView(): WebView? {
        return null
    }

    protected fun getWebLayout(): IWebLayout<*, *>? {
        return null
    }

    protected fun getPermissionInterceptor(): PermissionInterceptor? {
        return null
    }

    @NonNull
    protected fun getMiddleWareWebChrome(): MiddlewareWebChromeBase {
        return object : MiddlewareWebChromeBase() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                setTitle(view, title)
            }
        }.also { mMiddleWareWebChrome = it }
    }

    @NonNull
    protected fun getMiddleWareWebClient(): MiddlewareWebClientBase {
        return object : MiddlewareWebClientBase() {}.also { mMiddleWareWebClient = it }
    }

    override fun beforeInitView(bundle: Bundle?) {
    }

    override fun initView(bundle: Bundle?) {
    }

    override fun afterInitView(bundle: Bundle?) {
    }

    override fun getLayoutId(): Int = 0
}