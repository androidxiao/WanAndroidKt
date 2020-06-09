package com.black.multi.videosample.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by wei.
 * Date: 2020/6/9 9:45
 * Desc:
 */
@Database(entities = [SearchEntity::class], version = 1)
abstract class SearchTextDatabase : RoomDatabase() {

    abstract fun searchDao(): SearchDao

    companion object {
        @Volatile
        private var instance: SearchTextDatabase? = null

        fun getInstance(context: Context): SearchTextDatabase = instance ?: synchronized(this) {
            instance ?: createDb(context).also { instance = it }
        }

        private fun createDb(context: Context) =
                Room.databaseBuilder(context.applicationContext, SearchTextDatabase::class.java, "search.db").build()
    }

}