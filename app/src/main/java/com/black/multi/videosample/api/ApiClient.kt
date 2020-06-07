package com.black.multi.videosample.api

import com.black.xcommon.utils.AppGlobals
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by wei.
 * Date: 2020/5/18 下午11:49
 * Description:
 */
object ApiClient {

    const val BASE_URL = "https://www.wanandroid.com/"
    private const val CONNECT_TIME = 20L
    private const val WRITE_TIME = 20L
    private const val READ_TIME = 20L
    private val okHttpClient by lazy { OkHttpClient() }

    private val retrofit: Retrofit by lazy {
        val builder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        //添加 coolie
        val cookieJar: ClearableCookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(
            AppGlobals.getApplication()))
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = okHttpClient.newBuilder()
                .connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME, TimeUnit.SECONDS)
                .readTimeout(READ_TIME, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cookieJar(cookieJar)
                .build()
        builder.client(client).build()
    }

    fun <T> createService(tClass: Class<T>?): T {
        return retrofit.create(tClass)
    }
}