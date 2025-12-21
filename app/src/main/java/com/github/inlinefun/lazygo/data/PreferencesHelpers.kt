package com.github.inlinefun.lazygo.data

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed class PreferenceKey<A, B>(
    val id: String,
    @get:StringRes
    val label: Int,
    val defaultValue: A,
    val visibility: @Composable (PreferencesStore) -> Boolean,
    val serializer: PreferenceSerializer<A, B>
) {
    abstract fun datastoreKey(): Preferences.Key<B>
}

sealed interface PreferenceSerializer<A, B> {
    fun serialize(value: A): B
    fun deserialize(value: B): A
    class EnumSerializer<T: Enum<T>>(
        val values: Array<T>
    ): PreferenceSerializer<T, String> {
        override fun serialize(value: T): String = value.name
        override fun deserialize(value: String): T = values
            .firstOrNull {
                it.name.equals(value, true)
            }
            ?: values[0]
    }
    class DefaultSerializer<T>: PreferenceSerializer<T, T> {
        override fun serialize(value: T): T = value
        override fun deserialize(value: T): T = value
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
    fun <A, B> asStateFlow(key: PreferenceKey<A, B>, scope: CoroutineScope): StateFlow<A> {
        return flow(key)
            .stateIn(
                scope = scope,
                started = SharingStarted.Eagerly,
                initialValue = key.defaultValue
            )
    }
}

sealed class EnumPreferenceKey<T: PreferenceEnum>(
    id: String,
    @get:StringRes
    label: Int,
    defaultValue: T,
    visibility: @Composable (PreferencesStore) -> Boolean = { true },
    serializer: PreferenceSerializer<T, String>
): PreferenceKey<T, String>(
    id, label, defaultValue, visibility, serializer
) {
    override fun datastoreKey() = stringPreferencesKey(
        name = id
    )
}

sealed class BooleanPreferenceKey(
    id: String,
    @get:StringRes
    label: Int,
    visibility: @Composable (PreferencesStore) -> Boolean = { true },
    defaultValue: Boolean
): PreferenceKey<Boolean, Boolean>(
    id, label, defaultValue, visibility,
    serializer = PreferenceSerializer.DefaultSerializer()
) {
    override fun datastoreKey() = booleanPreferencesKey(
        name = id
    )
}
sealed class IntPreferenceKey(
    id: String,
    @get:StringRes
    label: Int,
    defaultValue: Int,
    visibility: @Composable (PreferencesStore) -> Boolean = { true },
    val minimum: Int,
    val maximum: Int,
    val prefix: String? = null,
    val suffix: String? = null
): PreferenceKey<Int, Int>(
    id, label, defaultValue, visibility,
    serializer = PreferenceSerializer.DefaultSerializer()
) {
    override fun datastoreKey() = intPreferencesKey(
        name = id
    )
}
sealed class FloatPreferenceKey(
    id: String,
    @get:StringRes
    label: Int,
    defaultValue: Float,
    visibility: @Composable (PreferencesStore) -> Boolean = { true },
    val minimum: Float,
    val maximum: Float,
    val prefix: String? = null,
    val suffix: String? = null
): PreferenceKey<Float, Float>(
    id, label, defaultValue, visibility,
    serializer = PreferenceSerializer.DefaultSerializer()
) {
    override fun datastoreKey() = floatPreferencesKey(
        name = id
    )
}
sealed interface PreferenceEnum {
    @get:StringRes
    val resourceId: Int
}

internal data class PreferenceCategory(
    @get:StringRes
    val label: Int,
    val preferences: List<PreferenceKey<*, *>>
)
