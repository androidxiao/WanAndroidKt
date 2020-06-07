package com.black.xcommon.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.black.xcommon.imageloader.ImageLoader
import com.black.xcommon.imageloader.glide.ImageConfigImpl

/**
 * Created by wei.
 * Date: 2020/5/29 9:20
 * Desc:
 */
object BindImage {

    @BindingAdapter("app:url")
    @JvmStatic
    fun bindImage(iv:ImageView,url:String){
        ImageLoader.getInstance().loadImage<ImageConfigImpl>(iv.context,
                ImageConfigImpl.builder().url(url)
                        .cacheStrategy(1)
                        .imageRadius(8)
                        .isCircle(false).imageView(iv).build())
    }
}