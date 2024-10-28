package com.moondroid.damoim.data.di

import com.moondroid.damoim.data.repository.ProfileRepositoryImpl
import com.moondroid.damoim.domain.repository.AppRepository
import com.moondroid.damoim.domain.repository.GroupRepository
import com.moondroid.damoim.domain.repository.ProfileRepository
import com.moondroid.damoim.domain.repository.SignRepository
import com.moondroid.damoim.data.repository.AppRepositoryImpl
import com.moondroid.damoim.data.repository.GroupRepositoryImpl
import com.moondroid.damoim.data.repository.MoimRepositoryImpl
import com.moondroid.damoim.data.repository.SignRepositoryImpl
import com.moondroid.damoim.domain.repository.MoimRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideGroupRepository(repository: GroupRepositoryImpl): GroupRepository

    @Binds
    fun provideProfileRepository(repository: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun provideSignRepository(repository: SignRepositoryImpl): SignRepository

    @Binds
    fun provideAppRepository(repository: AppRepositoryImpl): AppRepository

    @Binds
    fun provideMoimRepository(repository: MoimRepositoryImpl) : MoimRepository
}


