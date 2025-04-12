package com.example.gifviewer.viewmodels

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gifviewer.BuildConfig
import com.example.gifviewer.models.GifRequestData
import com.example.gifviewer.network.ApiReader
import com.example.gifviewer.network.ApiResponse
import com.example.gifviewer.network.hasInternetConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class)
class GifViewModel(private val gifListReader: ApiReader<List<GifRequestData>>) : ViewModel() {
    private val limit = 20

    private val _gifValues = MutableStateFlow(emptyList<GifRequestData>())
    val gifValues = _gifValues.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val currentPage = AtomicInteger(0)

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun addGifs(context: Context) {
        if (_isLoading.value) return

        if (!hasInternetConnection(context)) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            val offset = currentPage.get() * limit
            when (val result =
                gifListReader.read(url = "https://api.giphy.com/v1/gifs/trending?api_key=${BuildConfig.API_KEY}&limit=$limit&offset=$offset")) {
                is ApiResponse.Error -> Toast.makeText(
                    context,
                    "Error: ${result.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()

                is ApiResponse.Success -> {
                    _gifValues.value = _gifValues.value + result.data
                    currentPage.incrementAndGet()
                }
            }
            _isLoading.value = false
        }
    }

}