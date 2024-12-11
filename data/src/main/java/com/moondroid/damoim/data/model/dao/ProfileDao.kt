package com.moondroid.damoim.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moondroid.damoim.data.model.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Query("SELECT * FROM profile")
    suspend fun getProfileList(): List<ProfileEntity>

    @Query("DELETE FROM profile")
    suspend fun deleteProfileAll()
}