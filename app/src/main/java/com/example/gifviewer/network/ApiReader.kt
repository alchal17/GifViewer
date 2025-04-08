package com.example.gifviewer.network

interface ApiReader<T> {
    suspend fun read(url: String): ApiResponse<T>
}