package com.black.xcommon.utils.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by wei.
 * Date: 2020/5/24 下午10:45
 * Description:
 */
class StrDateDeserializer : JsonDeserializer<Date?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = json.asJsonPrimitive.asString
        return try {
            format.parse(date)
        } catch (var7: ParseException) {
            null
        }
    }
}
