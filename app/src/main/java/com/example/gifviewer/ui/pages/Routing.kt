package com.example.gifviewer.ui.pages

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routing {
    @Serializable
    object MainPage : Routing

    @Serializable
    data class DetailedPage(val gifId: String, val title: String, val uniqueId: String) : Routing
}