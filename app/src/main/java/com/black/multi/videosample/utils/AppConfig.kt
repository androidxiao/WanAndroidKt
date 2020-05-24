package com.black.multi.videosample.utils

import android.content.res.AssetManager
import com.black.multi.videosample.model.BottomBar
import com.black.multi.videosample.model.Destination
import com.black.multi.videosample.utils.GsonUtils.getGson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by wei.
 * Date: 2020/5/24 下午10:36
 * Description:
 */
object AppConfig {
    private var sDestConfig: HashMap<String, Destination>? = null
    private var sBottomBar: BottomBar? = null
    fun getDestConfig(): HashMap<String, Destination>? {
        if (sDestConfig == null) {
            val content = parseFile("destination.json")
            sDestConfig =getGson().fromJson<HashMap<String,Destination>>(content,object :TypeToken<HashMap<String,Destination>>(){}.type)
        }
        return sDestConfig
    }

    fun getBottomBarConfig(): BottomBar? {
        if (sBottomBar == null) {
            val content = parseFile("main_tabs_config.json")
            sBottomBar = JSON.parseObject(content, BottomBar::class.java)
        }
        return sBottomBar
    }

    fun getSofaTabConfig(): SofaTab? {
        if (sSofaTab == null) {
            val content = parseFile("sofa_tabs_config.json")
            sSofaTab = JSON.parseObject(content, SofaTab::class.java)
            Collections.sort(
                sSofaTab.tabs,
                Comparator<Any> { o1, o2 -> if (o1.index < o2.index) -1 else 1 })
        }
        return sSofaTab
    }

    fun getFindTabConfig(): SofaTab? {
        if (sFindTabConfig == null) {
            val content = parseFile("find_tabs_config.json")
            sFindTabConfig = JSON.parseObject(content, SofaTab::class.java)
            Collections.sort(
                sFindTabConfig.tabs,
                Comparator<Any> { o1, o2 -> if (o1.index < o2.index) -1 else 1 })
        }
        return sFindTabConfig
    }

    private fun parseFile(fileName: String): String {
        val assets: AssetManager = AppGlobals.getApplication().getAssets()
        var `is`: InputStream? = null
        var br: BufferedReader? = null
        val builder = StringBuilder()
        try {
            `is` = assets.open(fileName)
            br = BufferedReader(InputStreamReader(`is`))
            var line: String? = null
            while (br.readLine().also { line = it } != null) {
                builder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                `is`?.close()
                br?.close()
            } catch (e: Exception) {
            }
        }
        return builder.toString()
    }
}
