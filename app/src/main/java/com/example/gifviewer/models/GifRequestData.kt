package com.example.gifviewer.models

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.util.UUID

@Serializable
@Stable
data class GifRequestData(
    val id: String,
    val title: String,

    @Transient
    val uniqueId: String = UUID.randomUUID().toString()
)
