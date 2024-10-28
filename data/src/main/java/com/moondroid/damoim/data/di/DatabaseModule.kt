package com.moondroid.damoim.data.di

import android.content.Context
import androidx.room.Room
import com.moondroid.damoim.data.datasource.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in Application Component (i.e. everywhere in the application)
    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            LocalDatabase::class.java,
            "Damoim.db"
        ).build()
    } // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideUserDao(db: LocalDatabase) = db.profileDao() // The reason we can implement a Dao for the database
}