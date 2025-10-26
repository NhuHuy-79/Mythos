package com.nhuhuy.mythos.app.navigation

import android.content.Context
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed interface NavigateAction {
    data object ToDetail: NavigateAction
    data object Back: NavigateAction
    data object ToWiki: NavigateAction
}

class Navigator(
    private val context: Context
){
    private val _navigateAction = MutableSharedFlow<NavigateAction>()
    val navigatorAction = _navigateAction.asSharedFlow()

    suspend fun navigate(action: NavigateAction){
        _navigateAction.emit(action)
    }
}