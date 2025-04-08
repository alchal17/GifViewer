package com.example.gifviewer.ui.pages

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gifviewer.R
import com.example.gifviewer.ui.elements.GifPreviewCard
import com.example.gifviewer.viewmodels.GifViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MainPage(
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val gifViewModel = koinViewModel<GifViewModel>()
    val gifs = gifViewModel.gifValues.collectAsState()
    val isLoading = gifViewModel.isLoading.collectAsState()

    val context = LocalContext.current

    val gridState = rememberLazyGridState()

    val isAtEndOfList by remember {
        derivedStateOf {
            val lastVisibleIndex =
                gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisibleIndex == gifs.value.size - 1
        }
    }

    LaunchedEffect(isAtEndOfList && !isLoading.value) {
        gifViewModel.addGifs(context)
    }

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.height(100.dp),
                title = {
                    Text(
                        "Featured GIFs",
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.oswald_bold))
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                columns = GridCells.Adaptive(minSize = 100.dp),
                contentPadding = PaddingValues(top = 5.dp, end = 5.dp, start = 5.dp),
                state = gridState
            ) {
                items(gifs.value, key = { it.uniqueId }) { gif ->
                    GifPreviewCard(
                        gifData = gif,
                        animatedVisibilityScope = animatedVisibilityScope
                    ) {
                        navController.navigate(
                            Routing.DetailedPage(
                                gifId = gif.id,
                                title = gif.title,
                                uniqueId = gif.uniqueId,
                            )
                        )
                    }
                }
            }
        }
    }
}