package com.black.multi.videosample.ui.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by wei.
 * Date: 2020/5/24 上午10:40
 * Description:
 */
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    lateinit var mBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeInitView(savedInstanceState)
        initView(savedInstanceState)
        afterInitView(savedInstanceState)
    }

    abstract fun beforeInitView(savedInstanceState:Bundle?)

    abstract fun getLayoutId():Int

    fun initView(savedInstanceState: Bundle?){
        performDataBinding()
    }

    abstract fun afterInitView(savedInstanceState: Bundle?)

    private  fun performDataBinding() {
        mBinding = DataBindingUtil.setContentView<B>(this, getLayoutId())
        mBinding.executePendingBindings()
    }
}