package com.black.multi.videosample.base.ui

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by wei.
 * Date: 2020/5/24 上午10:40
 * Description:
 */
const val alphaAnimationDuration = 500L

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

    open fun initView(savedInstanceState: Bundle?){
        performDataBinding()
    }

    abstract fun afterInitView(savedInstanceState: Bundle?)

    private  fun performDataBinding() {
        mBinding = DataBindingUtil.setContentView<B>(this, getLayoutId())
        mBinding.executePendingBindings()
    }

    open fun nightOrDay(from:Float, to :Float) {
        val window = window
        val valueAnimator = ValueAnimator.ofFloat(from, to)
        valueAnimator.duration = alphaAnimationDuration
        valueAnimator.addUpdateListener { animation ->
            val params = window.attributes
            params.alpha = animation?.animatedValue as Float
            window.attributes = params
        }
        valueAnimator.start();
    }
}