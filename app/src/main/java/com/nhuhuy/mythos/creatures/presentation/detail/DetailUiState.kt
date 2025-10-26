package com.nhuhuy.mythos.creatures.presentation.detail

import androidx.compose.runtime.Immutable
import com.nhuhuy.mythos.core.ui.component.ScreenState
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource

@Immutable
data class DetailUiState(
    val resource: Resource<Creature> = Resource.Loading,
    val imgUrl: String = "",
)
