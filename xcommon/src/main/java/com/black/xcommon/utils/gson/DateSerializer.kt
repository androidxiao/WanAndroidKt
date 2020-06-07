package com.black.xcommon.utils.gson

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.util.*

/**
 * Created by wei.
 * Date: 2020/5/24 下午10:44
 * Description:
 */
class DateSerializer : JsonSerializer<Date> {
    override fun serialize(
        src: Date,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.time)
    }
}
