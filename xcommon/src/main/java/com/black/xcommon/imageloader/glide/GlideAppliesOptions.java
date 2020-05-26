package com.black.xcommon.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;

/**
 * Created by wei.
 * Date: 2019-10-05 12:35
 * Description:
 */
public interface GlideAppliesOptions {

    /**
     * 配置 @{@link GlideAppliesOptions} 的自定义参数,此方法在 @{@link GlideAppliesOptions} 初始化时执行(@{@link GlideAppliesOptions} 在第一次被调用时初始化),只会执行一次
     *
     * @param context
     * @param builder {@link GlideBuilder} 此类被用来创建 Glide
     */
    void applyGlideOptions(Context context, GlideBuilder builder);
}

