package com.black.multi.videosample.utils.gson

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by wei.
 * Date: 2020/5/24 下午10:46
 * Description:
 */
class StrDateSerializer : JsonSerializer<Date> {
    override fun serialize(
        src: Date?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = format.format(src)
        return JsonPrimitive(date)
    }
}
