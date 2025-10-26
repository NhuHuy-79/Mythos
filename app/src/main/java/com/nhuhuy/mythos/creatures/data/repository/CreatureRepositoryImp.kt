package com.nhuhuy.mythos.creatures.data.repository

import com.nhuhuy.mythos.core.utils.LogUtils
import com.nhuhuy.mythos.creatures.data.mapper.toEntity
import com.nhuhuy.mythos.creatures.data.mapper.toModel
import com.nhuhuy.mythos.creatures.data.source.CreatureLocalDataSource
import com.nhuhuy.mythos.creatures.data.source.CreatureNetworkDataSource
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource
import com.nhuhuy.mythos.creatures.domain.repository.CreatureRepository
import javax.inject.Inject

class CreatureRepositoryImp @Inject constructor(
    private val localDataSource: CreatureLocalDataSource,
    private val networkDataSource: CreatureNetworkDataSource
) : CreatureRepository{

    private class NoCreaturesCachedException(msg: String): Exception(msg)

    override suspend fun fetchCreatures(): Resource<List<Creature>> {
        return try {
            val response = networkDataSource.fetchCreatureList().map { dTO -> dTO.toModel() }
            Resource.Success(response)
        } catch (e: Exception){
            /*LogUtils.exception(e)*/
            Resource.Failure(e)
        }
    }

    override suspend fun getCreaturesById(id: Int): Resource<Creature> {
        return try {
            val creature = localDataSource.getCreatureById(id).toModel()
            Resource.Success(creature)
        } catch (e: Exception) {
            LogUtils.exception(e)
            Resource.Failure(e)
        }
    }

    override suspend fun getCreatures(): Resource<List<Creature>> {
        return try {
            val list  = localDataSource.getAll().map { entity -> entity.toModel() }
            if (list.isEmpty()){
                Resource.Failure(NoCreaturesCachedException("No creatures cached"))
            } else {
                Resource.Success(list)
            }

        } catch (e: Exception) {
            LogUtils.exception(e)
            Resource.Failure(e)
        }
    }

    override suspend fun saveCreatures(creatures: List<Creature>) {
        localDataSource.insertAll(creatures)
    }
}