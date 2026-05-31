package com.github.inlinefun.lazygo.preferences

import androidx.annotation.StringRes
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

sealed interface Preference
sealed interface PreferenceEnum {
    @get:StringRes
    val label: Int
    val value: String
}

data class PreferenceCategory(
    @field:StringRes
    val label: Int,
    val preferences: List<PreferenceKey<*, *>>
)

sealed class PreferenceKey<A, B>(
    val id: String,
    @field:StringRes
    val label: Int,
    @field:StringRes
    val description: Int?,
    @field:StringRes
    val icon: Int?,
    val defaultValue: A,
    val serialize: (A) -> B,
    val deserialize: (B) -> A
) : Preference {

    abstract fun getDataStoreKey(): Preferences.Key<B>

    class Switch(
        id: String,
        @field:StringRes
        label: Int,
        @field:StringRes
        description: Int? = null,
        @field:StringRes
        icon: Int? = null,
        defaultValue: Boolean = false,
    ) : PreferenceKey<Boolean, Boolean>(
        id, label, description, icon, defaultValue,
        serialize = { it },
        deserialize = { it }
    ) {
        override fun getDataStoreKey(): Preferences.Key<Boolean> = booleanPreferencesKey(name = id)
    }

    class IntSlider(
        id: String,
        @field:StringRes
        label: Int,
        @field:StringRes
        description: Int? = null,
        @field:StringRes
        icon: Int? = null,
        defaultValue: Int,
        val min: Int,
        val max: Int,
    ) : PreferenceKey<Int, Int>(
        id, label, description, icon, defaultValue,
        serialize = { it.coerceIn(min, max) },
        deserialize = { it.coerceIn(min, max) }
    ) {
        override fun getDataStoreKey(): Preferences.Key<Int> = intPreferencesKey(name = id)
    }

    class FloatSlider(
        id: String,
        @field:StringRes
        label: Int,
        @field:StringRes
        description: Int? = null,
        @field:StringRes
        icon: Int? = null,
        defaultValue: Float,
        val min: Float,
        val max: Float
    ) : PreferenceKey<Float, Float>(
        id, label, description, icon, defaultValue,
        serialize = { it.coerceIn(min, max) },
        deserialize = { it.coerceIn(min, max) }
    ) {
        override fun getDataStoreKey(): Preferences.Key<Float> = floatPreferencesKey(name = id)
    }

    class Choice<T>(
        id: String,
        @field:StringRes
        label: Int,
        @field:StringRes
        description: Int? = null,
        @field:StringRes
        icon: Int? = null,
        defaultValue: T,
        enumClass: Class<T>
    ) : PreferenceKey<T, String>(
        id, label, description, icon, defaultValue,
        serialize = { it.value },
        deserialize = { name ->
            runCatching {
                java.lang.Enum.valueOf(enumClass, name)
            }.getOrDefault(defaultValue)
        }
    ) where T : Enum<T>, T : PreferenceEnum {
        override fun getDataStoreKey(): Preferences.Key<String> = stringPreferencesKey(name = id)
    }

}
