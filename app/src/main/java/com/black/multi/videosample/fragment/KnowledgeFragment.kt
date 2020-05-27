package com.black.multi.videosample.fragment

import android.os.Bundle
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.databinding.FragmentKnowledgeBinding
import com.black.multi.videosample.base.ui.BaseFragment
import com.black.multi.videosample.utils.KNOWLEDGE_PAGE

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = KNOWLEDGE_PAGE,asStartPage = false)
class KnowledgeFragment : BaseFragment<FragmentKnowledgeBinding>() {

    override fun beforeInitView(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_knowledge

    override fun afterInitView(savedInstanceState: Bundle?) {
    }


}