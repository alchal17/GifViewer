@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.gifviewer.ui.elements

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.gifviewer.models.GifRequestData

@Composable
fun SharedTransitionScope.GifPreviewCard(
    gifData: GifRequestData,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: () -> Unit,
) {

    Card(
        modifier = Modifier
            .width(100.dp)
            .height(150.dp)
            .clickable { onClick() }
    ) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data("https://media.giphy.com/media/${gifData.id}/200w.gif")
                .decoderFactory(GifDecoder.Factory())
                .size(Size(width = 40, height = 60))
                .build()
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(key = gifData.uniqueId),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}