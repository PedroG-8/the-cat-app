package com.myapps.thecatapp.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object HomeGraph: Route

    @Serializable
    data object Home: Route

    @Serializable
    data object Favourites: Route

    @Serializable
    data class Detail(
        val imageId: String
    ): Route
}