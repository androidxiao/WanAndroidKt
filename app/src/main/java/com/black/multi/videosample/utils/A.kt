package com.black.multi.videosample.utils

import com.black.multi.videosample.model.Destination
import com.black.multi.videosample.utils.GsonUtils.getGson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by wei.
 * Date: 2020/5/24 下午10:54
 * Description:
 */
class A {
    private var map: HashMap<String, Destination>? =
        null

    fun a() {
        map = getGson()
            .fromJson<HashMap<String, Destination>>(
                "",
                object :
                    TypeToken<HashMap<String?, Destination?>?>() {}.type
            )
    }
}