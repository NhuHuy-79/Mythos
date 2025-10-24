package com.nhuhuy.mythos.creatures.data.source

import com.nhuhuy.mythos.creatures.data.mapper.toModel
import com.nhuhuy.mythos.creatures.data.network.CreatureApi
import com.nhuhuy.mythos.creatures.data.network.CreatureDTO
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreatureNetworkDataSource @Inject constructor(
    private val api: CreatureApi,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun fetchCreatureList(): List<CreatureDTO> {
       return withContext(dispatcher){
          api.fetchCreatureList()
       }
    }
}