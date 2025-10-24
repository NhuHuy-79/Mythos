package com.nhuhuy.mythos.creatures.presentation.home.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nhuhuy.mythos.creatures.domain.model.Resource

@Composable
fun <T>NetworkStateHandler(
    modifier: Modifier,
    resource: Resource<T>,
    onSuccess: @Composable (T) -> Unit,
    onFailure: @Composable () -> Unit,
    onLoading: @Composable () -> Unit,
){
    AnimatedContent(
        modifier = modifier,
        targetState = resource
    ) { resource ->
        when (resource) {
            is Resource.Failure -> onFailure()
            Resource.Loading -> onLoading()
            is Resource.Success -> onSuccess(resource.data)
        }
    }
}