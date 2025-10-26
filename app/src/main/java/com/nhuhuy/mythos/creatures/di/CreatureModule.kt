package com.nhuhuy.mythos.creatures.di

import com.nhuhuy.mythos.creatures.data.local.room.CreatureDao
import com.nhuhuy.mythos.creatures.data.network.CreatureApi
import com.nhuhuy.mythos.creatures.data.repository.CreatureRepositoryImp
import com.nhuhuy.mythos.creatures.data.source.CreatureLocalDataSource
import com.nhuhuy.mythos.creatures.data.source.CreatureNetworkDataSource
import com.nhuhuy.mythos.creatures.domain.repository.CreatureRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CreatureModule {
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): CreatureApi = retrofit.create(CreatureApi::class.java)

    @Provides
    @Singleton
    fun provideCreatureRepository(
        localDataSource: CreatureLocalDataSource,
        networkDataSource: CreatureNetworkDataSource
    ): CreatureRepository {
        return CreatureRepositoryImp(localDataSource, networkDataSource)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        api: CreatureApi,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): CreatureNetworkDataSource = CreatureNetworkDataSource(api, dispatcher)

    @Provides
    @Singleton
    fun provideLocalDataSource(
        dao: CreatureDao,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): CreatureLocalDataSource = CreatureLocalDataSource(dao, dispatcher)
}