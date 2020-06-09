package com.black.multi.videosample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.black.multi.videosample.model.database.SearchEntity
import com.black.multi.videosample.model.database.SearchTextDatabase
import com.black.xcommon.utils.AppGlobals
import com.black.xcommon.utils.EzLog
import com.black.xcommon.utils.GsonUtils
import kotlinx.coroutines.Dispatchers

/**
 * Created by wei.
 * Date: 2020/6/9 10:00
 * Desc:
 */
class SearchDaoVM : ViewModel() {

    companion object {
        val instance: SearchDaoVM by lazy { SearchDaoVM() }
    }

    fun getSearchList(): LiveData<List<SearchEntity>> {
        return liveData(Dispatchers.IO) {
            AppGlobals.getApplication()?.let {
                val searchTextList = SearchTextDatabase.getInstance(it).searchDao().getSearchTextList()
                EzLog.d("searchList--->${GsonUtils.getGson().toJson(searchTextList)}")
                emit(searchTextList)
            }
        }
    }

    fun insertSearchText(entity: SearchEntity):LiveData<Long>{
        return liveData(Dispatchers.IO) {
            AppGlobals.getApplication()?.let {


                val insertCount = SearchTextDatabase.getInstance(it).searchDao().insertSearchText(entity)
                EzLog.d("insertCount--->${insertCount}-->${Thread.currentThread().name}")
                emit(insertCount)
            }
        }
    }

    fun deleteSearchText(entity: SearchEntity):LiveData<Int>{
        return liveData(Dispatchers.IO) {
            AppGlobals.getApplication()?.let {
                val deleteCount = SearchTextDatabase.getInstance(it).searchDao().deleteSearchText(entity)
                EzLog.d("deleteSearchText--->${deleteCount}")
                emit(deleteCount)
            }
        }
    }
}