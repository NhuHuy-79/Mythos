package com.nhuhuy.mythos.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object Home : Route()

    @Serializable
    data class Detail(val id: Int) : Route()

    @Serializable
    data class Wiki(val url: String, val name: String): Route()
}