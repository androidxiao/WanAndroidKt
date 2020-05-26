package com.black.xcommon.imageloader;

import android.widget.ImageView;

/**
 * Created by wei.
 * Date: 2019-10-05 12:31
 * Description:
 * 这里是图片加载配置信息的基类,定义一些所有图片加载框架都可以用的通用参数
 * 每个 {@link BaseImageLoaderStrategy} 应该对应一个 {@link ImageConfig} 实现类
 */
public class ImageConfig {
    protected String url;
    protected ImageView imageView;
    protected int placeholder;//占位符
    protected int errorPic;//错误占位符
    protected int resId;
    protected String sdImagePath;
    protected String assetsPath;
    protected String thumbnailUrl;//缩略图


    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }

    public int getResId() {
        return resId;
    }

    public String getSdImagePath() {
        return sdImagePath;
    }

    public String getAssetsPath() {
        return assetsPath;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}

