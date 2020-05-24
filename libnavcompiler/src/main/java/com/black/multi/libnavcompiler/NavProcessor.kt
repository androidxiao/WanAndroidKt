package com.black.multi.libnavcompiler

import com.black.multi.libnavannotation.ActivityDestination
import com.black.multi.libnavannotation.FragmentDestination
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.FileObject
import javax.tools.StandardLocation

/**
 * Created by wei.
 * Date: 2020/5/18 下午11:31
 * Description:
 */
//AutoService 和 自定义的文件夹 resources 冲突
//@AutoService(Processor::class)
class NavProcessor : AbstractProcessor() {

    private lateinit var messager: Messager
    private lateinit var filer: Filer
    private val OUTPUT_FILE_NAME = "destination.json"

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        messager = processingEnv.messager
        filer = processingEnv.filer
        messager.printMessage(Diagnostic.Kind.NOTE, "resourcePath---bbbbb>")
    }

    override fun getSupportedAnnotationTypes(): Set<String>? {
        val sets: MutableSet<String> =
            LinkedHashSet()
        sets.add(FragmentDestination::class.java.canonicalName)
        sets.add(ActivityDestination::class.java.canonicalName)
        return sets
    }

    override fun getSupportedSourceVersion(): SourceVersion? {
        return SourceVersion.latestSupported()
    }


    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        val fragmentElements =
            roundEnv!!.getElementsAnnotatedWith(
                FragmentDestination::class.java
            )
        val activityElements =
            roundEnv.getElementsAnnotatedWith(
                ActivityDestination::class.java
            )
        if (fragmentElements.isNotEmpty() || activityElements.isNotEmpty()) {
            val destMap =
                HashMap<String, JsonObject>()
            handlerDestionation(fragmentElements, FragmentDestination::class.java, destMap)
            handlerDestionation(activityElements, ActivityDestination::class.java, destMap)
            var fos: FileOutputStream? = null
            var osr: OutputStreamWriter? = null
            //app/src/main/assets
            try {
                val resource: FileObject = filer.createResource(
                    StandardLocation.CLASS_OUTPUT,
                    "",
                    OUTPUT_FILE_NAME
                )
                val resourcePath = resource.toUri().path
                messager.printMessage(Diagnostic.Kind.NOTE, "resourcePath--->$resourcePath")
                val appPath =
                    resourcePath.substring(0, resourcePath.indexOf("app") + 4)
                val assetsPath = appPath + "src/main/assets/"
                val file = File(assetsPath)
                if (!file.exists()) {
                    file.mkdirs()
                }
                val outputFile = File(file, OUTPUT_FILE_NAME)
                if (outputFile.exists()) {
                    outputFile.delete()
                }
                outputFile.createNewFile()
                val gson = Gson()
                val s = gson.toJson(destMap)
                fos = FileOutputStream(outputFile)
                osr = OutputStreamWriter(fos, "UTF-8")
                osr.write(s)
                osr.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                if (osr != null) {
                    try {
                        osr.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return false
    }

    private fun handlerDestionation(
        fragmentElements: Set<Element>,
        annotationClass: Class<out Annotation>,
        destMap: HashMap<String, JsonObject>
    ) {
        for (element in fragmentElements) {
            val typeElement = element as TypeElement
            var pageUrl: String? = null
            val clazzName = typeElement.qualifiedName.toString()
            val id = Math.abs(clazzName.hashCode())
            var needLogin = false
            var asStartPage = false
            var isFragment = false
            val annotation = typeElement.getAnnotation(annotationClass)
            if (annotation is FragmentDestination) {
                pageUrl = annotation.pageUrl
                needLogin = annotation.needLogin
                asStartPage = annotation.asStartPage
                isFragment = true
            } else {
                val destination = annotation as ActivityDestination
                pageUrl = destination.pageUrl
                needLogin = destination.needLogin
                asStartPage = destination.asStartPage
                isFragment = false
            }
            val iterator: Iterator<Map.Entry<String, JsonObject>> =
                destMap.entries.iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                val key = next.key
                val value = next.value
                val url = value["pageUrl"].asString
                if (pageUrl.equals(url, ignoreCase = true)) {
                    val preClazz = value["clazzName"].asString
                    messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        "$preClazz 已经使用了该 pageUrl，$clazzName 不能再使用相同的 pageUrl"
                    )
                    return
                }
            }
            val jsonObject = JsonObject()
            jsonObject.addProperty("id", id)
            jsonObject.addProperty("needLogin", needLogin)
            jsonObject.addProperty("asStartPage", asStartPage)
            jsonObject.addProperty("pageUrl", pageUrl)
            jsonObject.addProperty("clazzName", clazzName)
            jsonObject.addProperty("isFragment", isFragment)
            destMap[clazzName] = jsonObject
        }

    }
}