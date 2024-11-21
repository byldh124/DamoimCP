package com.moondroid.damoim.data.datasource.local

import com.moondroid.damoim.common.exception.DMException
import com.moondroid.damoim.data.model.dao.ProfileDao
import com.moondroid.damoim.data.model.entity.ProfileEntity
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val profileDao: ProfileDao) {
    suspend fun getProfile() = profileDao.getProfileList().lastOrNull()
    suspend fun insertProfile(profileEntity: ProfileEntity) = profileDao.insertProfile(profileEntity)
    suspend fun deleteProfile() = profileDao.deleteProfileAll()

    fun getId() : String = runBlocking {
        getProfile()?.id ?: throw DMException.ProfileException()
    }
}