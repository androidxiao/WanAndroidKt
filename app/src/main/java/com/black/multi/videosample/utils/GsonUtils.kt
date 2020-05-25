package com.black.multi.videosample.utils

import com.black.multi.videosample.utils.gson.DateDeserializer
import com.black.multi.videosample.utils.gson.DateSerializer
import com.black.multi.videosample.utils.gson.StrDateDeserializer
import com.black.multi.videosample.utils.gson.StrDateSerializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import java.util.*

/**
 * Created by wei.
 * Date: 2020/5/24 下午10:42
 * Description:
 */
object GsonUtils {
    private  var gsonBuilder: GsonBuilder?=null
    fun getGson(): Gson {
        val gsonBuilder = getGsonBuilder()
        return gsonBuilder.create()
    }

    fun initStrDate() {
        val gsonBuilder = getGsonBuilder()
        gsonBuilder.setDateFormat(1)
            .registerTypeAdapter(Date::class.java, StrDateSerializer())
            .registerTypeAdapter(
                Date::class.java, StrDateDeserializer()
            )
    }

    fun initLongDate() {
        val gsonBuilder = getGsonBuilder()
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss")
            .registerTypeAdapter(Date::class.java,
                DateSerializer()
            ).registerTypeAdapter(
                Date::class.java,
                DateDeserializer()
            )
    }

    fun registerTypeAdapter(type: Type?, typeAdapter: Any?) {
        val gsonBuilder = getGsonBuilder()
        gsonBuilder.registerTypeAdapter(type, typeAdapter)
    }

    private fun getGsonBuilder(): GsonBuilder {
        if (gsonBuilder == null) {
            gsonBuilder = GsonBuilder()
        }
        return gsonBuilder!!
    }
}
