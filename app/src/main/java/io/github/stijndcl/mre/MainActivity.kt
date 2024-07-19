package io.github.stijndcl.mre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.stijndcl.mre.ui.theme.MRETheme

object Destinations {
    const val HOME = "home"
    const val SCREEN1 = "screen1"
    const val SCREEN2 = "screen2"
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MRETheme {
                val navController = rememberNavController()

                SharedTransitionLayout(modifier = Modifier.fillMaxSize()) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        NavHost(
                            navController = navController,
                            // Changing the startDestination to Destinations.SCREEN1 solves the text issue
                            startDestination = Destinations.HOME
                        ) {
                            composable(Destinations.HOME) {
                                HomeScreen(
                                    navigateToScreen1 = { navController.navigate(route = Destinations.SCREEN1) }
                                )
                            }

                            composable(Destinations.SCREEN1) {
                                Screen1(
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@composable,
                                    navigateToScreen2 = { navController.navigate(route = Destinations.SCREEN2) }
                                )
                            }

                            composable(Destinations.SCREEN2) {
                                Screen2(
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@composable,
                                    navigateToScreen1 = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
