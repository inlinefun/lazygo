package com.github.inlinefun.lazygo.ui

import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.Json

sealed interface Constants {
    object Padding {
        val small = 12.dp
        val medium = 24.dp
    }
    object Spacing {
        val small = 6.dp
        val medium = 12.dp
    }
    object Sizing {
        val large = 96.dp
    }
    object Durations {
        const val MAP_MOVEMENTS = 200
    }
    @Suppress("unused")
    object MapZoom {
        const val CITY_LEVEL = 15f
        const val IDEAL = 17.5f
        const val STREET_LEVEL = 20f
    }
    object Helpers {
        val json = Json {
            ignoreUnknownKeys = true
        }
    }
}
