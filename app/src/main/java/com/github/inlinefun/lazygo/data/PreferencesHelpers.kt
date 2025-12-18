package com.github.inlinefun.lazygo.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class PreferenceKey<A, B>(
    val id: String,
    val defaultValue: A,
    val serializer: PreferenceSerializer<A, B>
) {
    abstract fun datastoreKey(): Preferences.Key<B>
}

sealed class EnumPreferenceKey<T>(
    id: String,
    defaultValue: T,
    serializer: PreferenceSerializer<T, String>
): PreferenceKey<T, String>(
    id, defaultValue, serializer
) {
    override fun datastoreKey() = stringPreferencesKey(
        name = id
    )
}

sealed interface PreferenceSerializer<A, B> {
    fun serialize(value: A): B
    fun deserialize(value: B): A
    class EnumSerializer<T: Enum<T>>(
        private val values: Array<T>
    ): PreferenceSerializer<T, String> {
        override fun serialize(value: T): String = value.name
        override fun deserialize(value: String): T = values
            .firstOrNull {
                it.name.equals(value, true)
            }
            ?: values[0]

    }
}

class PreferencesStore(
    private val datastore: DataStore<Preferences>
) {
    fun <A, B> flow(key: PreferenceKey<A, B>): Flow<A> {
        return datastore
            .data
            .map { data ->
                data[key.datastoreKey()]
                    ?.let(key.serializer::deserialize)
                    ?: key.defaultValue
            }
    }
    suspend fun <A, B> set(key: PreferenceKey<A, B>, value: A) {
        datastore
            .edit { data ->
                data[key.datastoreKey()] = key.serializer.serialize(value)
            }
    }
}
