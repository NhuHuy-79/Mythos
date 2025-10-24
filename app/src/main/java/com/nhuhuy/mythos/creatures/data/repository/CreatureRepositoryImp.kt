package com.nhuhuy.mythos.creatures.data.repository

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
    override suspend fun fetchCreatures(): Resource<List<Creature>> {
        return try {
            val response = networkDataSource.fetchCreatureList().map { dTO -> dTO.toModel() }
            Resource.Success(response)
        } catch (e: Exception){
            Resource.Failure(e)
        }
    }

    override suspend fun getCreaturesById(id: Int): Creature {
        return localDataSource.getCreatureById(id).toModel()
    }

    override suspend fun getCreatures(): List<Creature> {
        return localDataSource.getAll().map { entity -> entity.toModel() }
    }

}