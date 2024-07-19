package io.github.stijndcl.mre

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private const val SHARED_BOX_STATE_KEY = 0

@Composable
fun HomeScreen(
    navigateToScreen1: () -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Text(text = "This is the home screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = navigateToScreen1) {
            Text(text = "Go to screen 1")
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Screen1(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToScreen2: () -> Unit = {}
) {
    ScreenWrapper(sharedTransitionScope, animatedVisibilityScope) {
        Button(onClick = navigateToScreen2) {
            Text(text = "Go to screen 2")
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Screen2(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToScreen1: () -> Unit = {}
) {
    ScreenWrapper(sharedTransitionScope, animatedVisibilityScope) {
        Button(onClick = navigateToScreen1) {
            Text(text = "Go back to screen 1")
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ScreenWrapper(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    content: @Composable (Modifier) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.statusBars)
    ) {
        with(sharedTransitionScope) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    // Box gets a different colour to help see that it does not disappear,
                    // but only the text inside of it does
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .sharedElement(
                        state = sharedTransitionScope.rememberSharedContentState(key = SHARED_BOX_STATE_KEY),
                        animatedVisibilityScope
                    )
            ) {
                Text(text = "This text vanishes for some reason")
            }
        }

        content(Modifier.weight(1f, true))
    }
}