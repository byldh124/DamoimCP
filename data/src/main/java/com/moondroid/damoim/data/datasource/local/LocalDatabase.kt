package com.moondroid.damoim.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moondroid.damoim.data.model.dao.ProfileDao
import com.moondroid.damoim.data.model.entity.ProfileEntity

@Database(entities = [ProfileEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}