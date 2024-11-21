package com.moondroid.damoim.data.model.dao

import androidx.room.*
import com.moondroid.damoim.common.exception.DMException
import com.moondroid.damoim.data.model.entity.ProfileEntity
import kotlinx.coroutines.runBlocking

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Query("SELECT * FROM profile")
    suspend fun getProfileList(): List<ProfileEntity>

    @Query("DELETE FROM profile")
    suspend fun deleteProfileAll()
}