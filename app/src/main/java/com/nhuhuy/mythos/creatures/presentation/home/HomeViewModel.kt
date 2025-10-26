package com.nhuhuy.mythos.creatures.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhuhuy.mythos.core.utils.filterName
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource
import com.nhuhuy.mythos.creatures.domain.model.then
import com.nhuhuy.mythos.creatures.domain.usecase.FetchCreaturesUseCase
import com.nhuhuy.mythos.creatures.domain.usecase.ObserveCreaturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
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
    val state = _state.asStateFlow()

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


     fun observeCreatureList() {
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