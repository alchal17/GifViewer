package com.example.gifviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.gifviewer.ui.pages.DetailPage
import com.example.gifviewer.ui.pages.MainPage
import com.example.gifviewer.ui.pages.Routing
import com.example.gifviewer.ui.theme.GifViewerTheme

@OptIn(ExperimentalSharedTransitionApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            GifViewerTheme {
                SharedTransitionLayout {
                    NavHost(
                        navController = navController,
                        startDestination = Routing.MainPage
                    ) {
                        composable<Routing.MainPage> {
                            MainPage(navController, this)
                        }
                        composable<Routing.DetailedPage> {
                            val args = it.toRoute<Routing.DetailedPage>()
                            DetailPage(
                                gifId = args.gifId,
                                title = args.title,
                                navController = navController,
                                animatedVisibilityScope = this,
                                uniqueId = args.uniqueId
                            )
                        }
                    }
                }
            }
        }
    }
}
