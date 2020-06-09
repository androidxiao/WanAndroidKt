package com.black.multi.videosample.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by wei.
 * Date: 2020/6/9 9:35
 * Desc:
 */

@Entity
class SearchEntity{
    //主键是否自动增长，默认为false
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
    var searchText:String?=null
}