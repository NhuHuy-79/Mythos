package com.nhuhuy.mythos.creatures.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhuhuy.mythos.creatures.domain.usecase.FetchCreaturesUseCase
import com.nhuhuy.mythos.creatures.domain.usecase.ObserveCreaturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeCreaturesUseCase: ObserveCreaturesUseCase,
    private val fetchCreaturesUseCase: FetchCreaturesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state
        .onStart {
            observeCreatureList()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

    fun updateSearchQuery(query: String) {
       _state.update {
           it.copy(query = query)
       }
    }
    fun changeSearchStatus(value: Boolean) {
        _state.update {
            it.copy(isSearching = value)
        }
    }

    fun onRetry(){
        viewModelScope.launch {
            fetchCreaturesUseCase()
        }
    }

     private fun observeCreatureList() {
        viewModelScope.launch {
            observeCreaturesUseCase().collect { resource ->
                _state.update {
                    it.copy(result = resource)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel Status", "isClear")
    }
}