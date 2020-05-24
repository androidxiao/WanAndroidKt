package com.black.multi.videosample.ui

import androidx.lifecycle.LifecycleOwner
import com.black.multi.videosample.ui.base.baseadapter.IRecycleViewCallback
import com.black.multi.videosample.ui.base.baseadapter.SimpleBaseAdapter

/**
 * Created by wei.
 * Date: 2020/5/24 下午6:18
 * Description:
 */
class RvAdapter(owner: LifecycleOwner,callback: IRecycleViewCallback<String>) :
    SimpleBaseAdapter<XHolder, String>(owner,callback)