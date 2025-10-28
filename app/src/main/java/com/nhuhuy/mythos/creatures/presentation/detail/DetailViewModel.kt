package com.nhuhuy.mythos.creatures.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhuhuy.mythos.creatures.domain.usecase.GetCreatureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCreatureById: GetCreatureUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()


    fun updateImageState(key: String) {
       _state.update {
           it.copy(imgUrl = key)
       }
    }

    fun getCreatureDetailById(id: Int) {
        viewModelScope.launch {
            val resource = getCreatureById(id)
            _state.update {
                it.copy(resource = resource)
            }
        }
    }
}