package com.nhuhuy.mythos.creatures.domain.usecase

import com.nhuhuy.mythos.core.utils.LogUtils
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
        val creatures = creatureRepository.getCreatures()
        if (creatures.isEmpty()){
            val result = creatureRepository.fetchCreatures()
            result.then(
                failure = { throwable ->
                    LogUtils.exception(throwable)
                },
                success = { creatures ->
                    emit(Resource.Success(creatures))
                }
            )
        } else {
            emit(Resource.Success(creatures))
        }
    }
}