package com.nhuhuy.mythos.creatures.domain.usecase

import com.nhuhuy.mythos.core.utils.LogUtils
import com.nhuhuy.mythos.creatures.data.mapper.toEntity
import com.nhuhuy.mythos.creatures.domain.model.Resource
import com.nhuhuy.mythos.creatures.domain.model.then
import com.nhuhuy.mythos.creatures.domain.repository.CreatureRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObserveCreaturesUseCase @Inject constructor(
    private val creatureRepository: CreatureRepository
) {
    operator fun invoke() = flow {
        emit(Resource.Loading)
        val resource = creatureRepository.getCreatures()
        when (resource) {
            is Resource.Failure -> {
                val response = creatureRepository.fetchCreatures()
                response.then(
                    failure = { throwable ->
                        LogUtils.exception(throwable)
                        emit(resource)
                    },
                    success = { creatures ->
                        creatureRepository.saveCreatures(creatures)
                        emit(Resource.Success(creatures))
                    }
                )
            }
            Resource.Loading -> emit(resource)
            is Resource.Success -> emit(resource)
        }
    }
}