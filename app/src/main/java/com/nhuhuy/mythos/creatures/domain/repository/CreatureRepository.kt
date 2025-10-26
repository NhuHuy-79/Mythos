package com.nhuhuy.mythos.creatures.domain.repository

import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource

interface CreatureRepository {
    suspend fun fetchCreatures() : Resource<List<Creature>>
    suspend fun getCreaturesById(id: Int): Resource<Creature>
    suspend fun getCreatures(): Resource<List<Creature>>
    suspend fun saveCreatures(creatures: List<Creature>)
}

