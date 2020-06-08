package com.black.xcommon.utils

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.black.xcommon.imageloader.ImageLoader
import com.black.xcommon.imageloader.glide.ImageConfigImpl

/**
 * Created by wei.
 * Date: 2020/5/29 9:20
 * Desc:
 */
object BindData {

    @BindingAdapter("app:url")
    @JvmStatic
    fun bindImage(iv:ImageView,url:String){
        ImageLoader.getInstance().loadImage<ImageConfigImpl>(iv.context,
                ImageConfigImpl.builder().url(url)
                        .cacheStrategy(1)
                        .imageRadius(8)
                        .isCircle(false).imageView(iv).build())
    }

    @BindingAdapter("app:title")
    @JvmStatic
    fun bindHtmlText(tv: TextView, text: String) {
        tv.text = Html.fromHtml(text)
    }
}