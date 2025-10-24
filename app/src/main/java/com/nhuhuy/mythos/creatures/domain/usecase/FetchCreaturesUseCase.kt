package com.nhuhuy.mythos.creatures.domain.usecase

import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource
import com.nhuhuy.mythos.creatures.domain.repository.CreatureRepository
import javax.inject.Inject

class FetchCreaturesUseCase @Inject constructor(
    private val creatureRepository: CreatureRepository
) {
    suspend operator fun invoke(): Resource<List<Creature>> {
        return creatureRepository.fetchCreatures()
    }
}
