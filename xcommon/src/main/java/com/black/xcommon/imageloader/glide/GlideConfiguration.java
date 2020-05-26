package com.black.xcommon.imageloader.glide;

import android.content.Context;

import com.black.xcommon.imageloader.BaseImageLoaderStrategy;
import com.black.xcommon.imageloader.ImageLoader;
import com.black.xcommon.imageloader.glidehttp.OkHttpUrlLoader;
import com.black.xcommon.utils.EzLog;
import com.black.xcommon.utils.FileUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;


import java.io.File;
import java.io.InputStream;

import okhttp3.OkHttpClient;


/**
 * Created by wei.
 * Date: 2019-10-05 12:36
 * Description:
 */
@GlideModule(glideName = "CommonGlide")
public class GlideConfiguration extends AppGlideModule {

    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;//图片缓存文件最大值为100Mb

    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {

        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                EzLog.d("Glide 缓存设置-------》");
                // Careful: the external cache directory doesn't enforce permissions
                EzLog.d("Glide 缓存路径----》"+ FileUtils.getCacheFilePath(context));
                return DiskLruCacheWrapper.create(FileUtils.makeDirs(new File(FileUtils.getCacheFile(context), "Glide")), IMAGE_DISK_CACHE_MAX_SIZE);
            }
        });

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
        BaseImageLoaderStrategy loadImgStrategy = ImageLoader.getInstance().getLoadImgStrategy();
        ((GlideAppliesOptions) loadImgStrategy).applyGlideOptions(context, builder);
        EzLog.d("Glide  缓存设置结束-------》");
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        EzLog.d("Glide 使用 OkHttp3.0 框架");
        //Glide 默认使用 HttpURLConnection 做网络请求,在这切换成 Okhttp 请求
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(new OkHttpClient()));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}

