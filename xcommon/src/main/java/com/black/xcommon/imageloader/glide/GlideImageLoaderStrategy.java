package com.black.xcommon.imageloader.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.black.xcommon.imageloader.BaseImageLoaderStrategy;
import com.black.xcommon.utils.EzLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;


/**
 * Created by wei.
 * Date: 2019-10-05 12:39
 * Description:
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<ImageConfigImpl>, GlideAppliesOptions {

    @Override
    public void loadImage(Context ctx, final ImageConfigImpl config) {

        GlideRequests requests;

        requests = (GlideRequests) Glide.with(ctx);
        //如果context是activity则自动使用Activity的生命周期
        GlideRequest<Drawable> glideRequest;
        if (config.isResId()) {
            glideRequest = requests.load(config.getResId());
        } else if (config.isSdPath()) {
            glideRequest = requests.load("file://" + config.getSdImagePath());
        } else if (config.isAssets()) {
            glideRequest = requests.load("file:///android_asset/" + config.getAssetsPath());
        } else {
            glideRequest = requests.load(config.getUrl());
        }

        switch (config.getCacheStrategy()) {
            //缓存策略
            case 1:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case 3:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case 4:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
        }

        if (config.isCrossFade()) {
            glideRequest.transition(DrawableTransitionOptions.withCrossFade());
        }

        if (config.isCenterCrop()) {
            glideRequest.centerCrop();
        }

        if (config.isCircle()) {
            glideRequest.circleCrop();
        }

        if (config.isImageRadius()) {
            glideRequest.transform(new RoundedCorners(config.getImageRadius()));
        }

//        if (config.isBlurImage()) {
//            glideRequest.transform(new BlurTransformation(config.getBlurValue()));
//        }

        if (config.getTransformation() != null) {//glide用它来改变图形的形状
            glideRequest.transform(config.getTransformation());
        }

        if (config.getPlaceholder() != 0)//设置占位符
            glideRequest.placeholder(config.getPlaceholder());

        if (config.getErrorPic() != 0)//设置错误的图片
            glideRequest.error(config.getErrorPic());

        if (config.getFallback() != 0)//设置请求 url 为空图片
            glideRequest.fallback(config.getFallback());

        if (!TextUtils.isEmpty(config.getThumbnailUrl())) {
            glideRequest.thumbnail(Glide.with(ctx).load(config.getThumbnailUrl()));
        }

        if (config.isCustomInto()) {

            glideRequest.into(new SimpleTarget<Drawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                @Override
                public void onResourceReady(@NonNull final Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    if (config.getGlideImageLoadListener() != null) {
                        config.getGlideImageLoadListener().onResourceReady(resource, transition);
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    if (config.getGlideImageLoadListener() != null) {
                        config.getGlideImageLoadListener().onLoadFailed(errorDrawable);
                    }
                }
            });
        } else {
            glideRequest
                    .into(config.getImageView());
        }
    }

    @Override
    public void clear(final Context ctx, ImageConfigImpl config) {

        if (config.getImageViews() != null && config.getImageViews().length > 0) {//取消在执行的任务并且释放资源
            for (ImageView imageView : config.getImageViews()) {
                Glide.get(ctx).getRequestManagerRetriever().get(ctx).clear(imageView);
            }
        }

        if (config.isClearDiskCache()) {//清除本地缓存
            Glide.get(ctx).clearDiskCache();
        }

        if (config.isClearMemory()) {//清除内存缓存

            Glide.get(ctx).clearMemory();
        }

    }


    @Override
    public void applyGlideOptions(Context context, GlideBuilder builder) {
        EzLog.d("---------------------applyGlideOptions----------------------");
    }

    public interface IGlideIntoListener {

        void onResourceReady(@NonNull final Drawable resource, @Nullable Transition<? super Drawable> transition);

        void onLoadFailed(@Nullable Drawable errorDrawable);
    }
}

