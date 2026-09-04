package com.github.inlinefun.lazygo.data.preferences

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.preferences by preferencesDataStore(name = "preferences")

fun <A, B> Context.getPreference(preference: PreferenceType<A, B>): Flow<A> {
    return this
        .preferences
        .data
        .map { data ->
            data[preference.getDataStoreKey()]
                ?.let { value ->
                    preference.deserialize(value)
                } ?: preference.defaultValue
        }
}

@Composable
fun <A, B> Context.getPreferenceAsState(preference: PreferenceType<A, B>): State<A> {
    return this
        .getPreference(preference)
        .collectAsState(
            initial = preference.defaultValue
        )
}

suspend fun <A, B> Context.setPreference(preference: PreferenceType<A, B>, value: A) {
    this
        .preferences
        .edit { data ->
            data[preference.getDataStoreKey()] = preference.serialize(value)
        }
}
