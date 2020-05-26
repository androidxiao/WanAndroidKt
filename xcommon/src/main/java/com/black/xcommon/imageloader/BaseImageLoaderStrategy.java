package com.black.xcommon.imageloader;

import android.content.Context;

/**
 * Created by wei.
 * Date: 2019-10-05 12:32
 * Description:
 * 图片加载策略,实现 {@link BaseImageLoaderStrategy}
 * 并通过 {@link ImageLoader#setLoadImgStrategy(BaseImageLoaderStrategy)} 配置后,才可进行图片请求
 */
public interface BaseImageLoaderStrategy<T extends ImageConfig> {
    /**
     * 加载图片
     *
     * @param ctx
     * @param config
     */
    void loadImage(Context ctx, T config);

    /**
     * 停止加载
     *
     * @param ctx
     * @param config
     */
    void clear(Context ctx, T config);
}