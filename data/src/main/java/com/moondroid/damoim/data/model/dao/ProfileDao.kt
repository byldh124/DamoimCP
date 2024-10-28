package com.moondroid.damoim.data.model.dao

import androidx.room.*
import com.moondroid.damoim.data.model.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Query("SELECT * FROM profile")
    suspend fun getProfileList(): List<ProfileEntity>

    @Query("DELETE FROM profile")
    suspend fun deleteProfileAll()

    suspend fun getProfile(): ProfileEntity? = getProfileList().lastOrNull()
}