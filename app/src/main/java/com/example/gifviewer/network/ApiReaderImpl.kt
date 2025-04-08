package com.example.gifviewer.network

import com.example.gifviewer.models.GifRequestData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

class ApiReaderImpl(private val client: HttpClient) : ApiReader<List<GifRequestData>> {

    @Serializable
    private data class RequestResult(val data: List<GifRequestData>)

    override suspend fun read(url: String): ApiResponse<List<GifRequestData>> {
        return try {
            val result = client.get(url)
            if (result.status == HttpStatusCode.OK) {
                val gifData = result.body<RequestResult>().data
                ApiResponse.Success(data = gifData)
            } else {
                ApiResponse.Error(message = result.bodyAsText())
            }
        } catch (e: Exception) {
            ApiResponse.Error(message = e.message ?: "Unknown error")
        }
    }
}