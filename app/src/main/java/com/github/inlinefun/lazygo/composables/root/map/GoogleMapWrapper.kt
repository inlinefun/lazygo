package com.github.inlinefun.lazygo.composables.root.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun GoogleMapWrapper() {
    val loadTime = 1000L
    var loadMap by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(loadTime)
        loadMap = true
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularWavyProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
        AnimatedVisibility(
            visible = loadMap,
            modifier = Modifier
                .fillMaxSize()
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}
