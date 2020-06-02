package com.black.multi.videosample.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by wei.
 * Date: 2020/6/2 11:05
 * Desc: 基本用法
 * <p>
 * var spString1: String by sharedPreferences.string(name = "spString1")
 * var spString2: String by sharedPreferences.string(name = "spString2", defValue = "def")
 * var spString3: String? by sharedPreferences.nullableString(name = "spString3", defValue = "def")
 * var spString4: String? by sharedPreferences.nullableString(name = "spString4", defValue = null)
 * var spBoolean: Boolean by sharedPreferences.boolean(name = "spBoolean")
 * var spFloat: Float by sharedPreferences.float(name = "spFloat", defValue = 1f)
 * </p>
 */
object SharedPreferencesUtils {

    abstract class Delegates {

        private val preferences: SharedPreferences by lazy {
           AppGlobals.getApplication()!!.getSharedPreferences(
                    getSharedPreferencesName(),
                    Context.MODE_PRIVATE
            )
        }

        fun int(defaultValue: Int = 0) = object : ReadWriteProperty<Any, Int> {
            override fun getValue(thisRef: Any, property: KProperty<*>): Int {
                return preferences.getInt(property.name, defaultValue)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
                preferences.edit().putInt(property.name, value).apply()
            }
        }

        fun string(defaultValue: String? = null) = object : ReadWriteProperty<Any, String?> {
            override fun getValue(thisRef: Any, property: KProperty<*>): String? {
                return preferences.getString(property.name, defaultValue)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
                preferences.edit().putString(property.name, value).apply()
            }
        }

        fun long(defaultValue: Long = 0L) = object : ReadWriteProperty<Any, Long> {

            override fun getValue(thisRef: Any, property: KProperty<*>): Long {
                return preferences.getLong(property.name, defaultValue)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
                preferences.edit().putLong(property.name, value).apply()
            }
        }

        fun boolean(defaultValue: Boolean = false) = object : ReadWriteProperty<Any, Boolean> {
            override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
                return preferences.getBoolean(property.name, defaultValue)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
                preferences.edit().putBoolean(property.name, value).apply()
            }
        }

        fun float(defaultValue: Float = 0.0f) = object : ReadWriteProperty<Any, Float> {
            override fun getValue(thisRef: Any, property: KProperty<*>): Float {
                return preferences.getFloat(property.name, defaultValue)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
                preferences.edit().putFloat(property.name, value).apply()
            }
        }

        fun setString(defaultValue: Set<String>? = null) = object :
                ReadWriteProperty<SharedPreferencesUtils, Set<String>?> {
            override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): Set<String>? {
                return preferences.getStringSet(property.name, defaultValue)
            }

            override fun setValue(thisRef: SharedPreferencesUtils, property: KProperty<*>, value: Set<String>?) {
                preferences.edit().putStringSet(property.name, value).apply()
            }
        }

        fun clearAll() {
            preferences.edit().clear().apply()
        }

        abstract fun getSharedPreferencesName(): String
    }
}