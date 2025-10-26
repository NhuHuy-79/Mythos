package com.nhuhuy.mythos.creatures.data.source

import com.nhuhuy.mythos.creatures.data.local.room.CreatureDao
import com.nhuhuy.mythos.creatures.data.local.room.CreatureEntity
import com.nhuhuy.mythos.creatures.data.mapper.toEntity
import com.nhuhuy.mythos.creatures.domain.model.Creature
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CreatureLocalDataSource(
    private val dao: CreatureDao,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun getAll(): List<CreatureEntity> {
        return withContext(dispatcher) {
            dao.getAll()
        }
    }

    suspend fun insertAll(creatures: List<Creature>) {
        withContext(dispatcher) {
            val entities = creatures.map {
                it.toEntity()
            }
            dao.insertAll(entities)
        }
    }

    suspend fun getCreatureById(id: Int): CreatureEntity {
        return withContext(dispatcher) {
            dao.getCreatureById(id.toLong())
        }
    }
}