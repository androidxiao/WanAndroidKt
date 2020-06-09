package com.black.multi.videosample.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by wei.
 * Date: 2020/6/9 9:38
 * Desc:
 */
@Dao
interface SearchDao {

    @Query("SELECT * FROM SearchEntity")
    suspend fun getSearchTextList():List<SearchEntity>

    @Insert
    suspend fun insertSearchText(entity: SearchEntity):Long

    @Insert
    suspend fun insertSearchText(vararg entity: SearchEntity):List<Long>

    @Delete
    suspend fun deleteSearchText(entity: SearchEntity):Int
}