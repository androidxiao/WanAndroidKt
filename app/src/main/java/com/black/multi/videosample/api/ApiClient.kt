package com.black.multi.videosample.api

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

    private const val BASE_URL = "https://www.wanandroid.com/"
    private const val CONNECT_TIME = 20L
    private const val WRITE_TIME = 20L
    private const val READ_TIME = 20L
    private val mRetrofitMap=HashMap<String,Retrofit>()
    private val okHttpClient by lazy { OkHttpClient() }

    private val retrofit: Retrofit by lazy {
        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = okHttpClient.newBuilder()
            .connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME, TimeUnit.SECONDS)
            .readTimeout(READ_TIME, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
        builder.client(client).build()
    }

    fun <T> createService(tClass: Class<T>?): T {
        //有了，直接取
        if(mRetrofitMap[BASE_URL+tClass?.name]!=null){
            return mRetrofitMap[BASE_URL+tClass?.name]!!.create(tClass)
        }
        //缓存 retrofit 实例
        mRetrofitMap[BASE_URL+tClass?.name] = retrofit
        return retrofit.create(tClass)
    }
}