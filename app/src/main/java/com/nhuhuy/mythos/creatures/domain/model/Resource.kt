package com.nhuhuy.mythos.creatures.domain.model

private class NoCreaturesCachedException(msg: String): Throwable(msg)


sealed class Resource<out T>(){
    data object Loading : Resource<Nothing>()
    data class Failure(val throwable: Throwable) : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
}

suspend fun <T>Resource<T>.then(
    failure: suspend (Throwable) -> Unit,
    success: suspend (T) -> Unit
){
    return when (this) {
        is Resource.Failure -> failure(this.throwable)
        Resource.Loading -> Unit
        is Resource.Success -> success(this.data)
    }
}