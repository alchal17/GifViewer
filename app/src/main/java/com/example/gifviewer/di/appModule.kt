package com.example.gifviewer.di

import com.example.gifviewer.models.GifRequestData
import com.example.gifviewer.network.ApiReader
import com.example.gifviewer.network.ApiReaderImpl
import com.example.gifviewer.viewmodels.GifViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    singleOf(::ApiReaderImpl) { bind<ApiReader<List<GifRequestData>>>() }

    viewModelOf(::GifViewModel)
}