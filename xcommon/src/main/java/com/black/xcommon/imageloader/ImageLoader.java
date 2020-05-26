package com.black.xcommon.imageloader;

import android.content.Context;

import com.black.xcommon.imageloader.glide.GlideImageLoaderStrategy;
import com.black.xcommon.imageloader.glide.ImageConfigImpl;


/**
 * Created by wei.
 * Date: 2019-10-05 12:33
 * Description:
 *  使用策略模式和建造者模式,可以动态切换图片请求框架(比如说切换成 Picasso )
 *  当需要切换图片请求框架或图片请求框架升级后变更了 Api 时
 *  这里可以将影响范围降到最低,所以封装 {@link ImageLoader} 是为了屏蔽这个风险
 */
public class ImageLoader {
    GlideImageLoaderStrategy mStrategy;

    private ImageLoader(){
        mStrategy=new GlideImageLoaderStrategy();
    }

    public static ImageLoader getInstance(){
        return ImageLoaderHolder.IMAGE_LOADER;
    }

    private static class ImageLoaderHolder{
        private static final ImageLoader IMAGE_LOADER = new ImageLoader();
    }


    /**
     * 加载图片
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends ImageConfig> void loadImage(Context context, ImageConfigImpl config) {
        mStrategy.loadImage(context, config);
    }

    /**
     * 停止加载或清理缓存
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends ImageConfig> void clear(Context context, ImageConfigImpl config) {
        mStrategy.clear(context, config);
    }

    public BaseImageLoaderStrategy getLoadImgStrategy() {
        return new GlideImageLoaderStrategy();
    }
}
