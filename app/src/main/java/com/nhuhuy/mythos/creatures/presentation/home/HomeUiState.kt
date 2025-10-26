package com.nhuhuy.mythos.creatures.presentation.home

import androidx.compose.runtime.Stable
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource

@Stable
data class HomeUiState(
    val result : Resource<List<Creature>> = Resource.Loading,
    val isSearching: Boolean = false,
    val isImageZoom: Boolean = false,
    val query: String = ""
)

