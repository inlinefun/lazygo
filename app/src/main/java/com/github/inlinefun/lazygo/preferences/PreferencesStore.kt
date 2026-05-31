package com.github.inlinefun.lazygo.preferences

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "preferences")

fun <A, B> Context.getPreference(key: PreferenceKey<A, B>): Flow<A> {
    return this
        .dataStore
        .data
        .map { data ->
            data[key.getDataStoreKey()]
                ?.let { value ->
                    key.deserialize(value)
                } ?: key.defaultValue
        }
}

@Composable
fun <A, B> Context.getPreferenceAsState(key: PreferenceKey<A, B>): State<A> {
    return this
        .getPreference(key)
        .collectAsState(
            initial = key.defaultValue
        )
}

suspend fun <A, B> Context.setPreference(key: PreferenceKey<A, B>, value: A) {
    this
        .dataStore
        .edit { data ->
            data[key.getDataStoreKey()] = key.serialize(value)
        }
}
