package com.black.multi.videosample.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.black.multi.videosample.utils.ShowHideBottomBar
import com.black.xcommon.utils.EzLog

/**
 * Created by wei.
 * Date: 2020/5/24 下午7:40
 * Description:
 */
abstract class BaseFragment<B : ViewDataBinding> :Fragment() {

    protected lateinit var binding: B
    protected var ivBack:ImageView?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        beforeInitView(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater!!, getLayoutId(), container, false)
        initView(savedInstanceState)
        navigationUp()
        afterInitView(savedInstanceState)
        return binding.root
    }

    private fun navigationUp(){
        if (ivBack != null) {
            ivBack?.setOnClickListener {
                val id = ShowHideBottomBar.instance.getId()
                val isMain = ShowHideBottomBar.instance.getIsMain()
                EzLog.d("navigationUp--finish--to--page--id--->${ShowHideBottomBar.instance.getId()}----isMain-->${isMain}")
                if (id != null && isMain != null && isMain) {
                    NavHostFragment.findNavController(this).popBackStack()
                }
            }
        }
    }

    abstract fun beforeInitView(bundle: Bundle?)

    abstract fun initView(bundle: Bundle?)

    abstract fun afterInitView(bundle: Bundle?)

    protected abstract fun getLayoutId(): Int
}