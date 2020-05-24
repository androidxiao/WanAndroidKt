package com.black.multi.videosample.ui.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created by wei.
 * Date: 2020/5/24 下午7:40
 * Description:
 */
abstract class BaseFragment<B : ViewDataBinding> :Fragment() {

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        beforeInitView(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater!!, getLayoutId(), container, false)
        initView(savedInstanceState)
        afterInitView(savedInstanceState)
        return binding.getRoot()
    }

    abstract fun beforeInitView(savedInstanceState:Bundle?)

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun afterInitView(savedInstanceState: Bundle?)

    protected abstract fun getLayoutId(): Int
}