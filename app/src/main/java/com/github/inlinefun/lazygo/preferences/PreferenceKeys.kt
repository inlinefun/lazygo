package com.github.inlinefun.lazygo.preferences

import androidx.annotation.StringRes
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlin.enums.EnumEntries

sealed interface Preference

sealed interface PreferenceEnum {
    @get:StringRes
    val label: Int
    val value: String
}

sealed class PreferenceKey<A, B>(
    val id: String,
    val defaultValue: A,
    val serialize: (A) -> B,
    val deserialize: (B) -> A
) : Preference {

    abstract fun getDataStoreKey(): Preferences.Key<B>

    class Switch(
        id: String,
        defaultValue: Boolean
    ) : PreferenceKey<Boolean, Boolean>(
        id,
        defaultValue,
        serialize = { it },
        deserialize = { it }
    ) {
        override fun getDataStoreKey(): Preferences.Key<Boolean> = booleanPreferencesKey(name = id)
    }

    class IntSlider(
        id: String,
        defaultValue: Int,
        val min: Int,
        val max: Int,
        val step: Int = 1
    ) : PreferenceKey<Int, Int>(
        id,
        defaultValue,
        serialize = { it },
        deserialize = { it }
    ) {
        override fun getDataStoreKey(): Preferences.Key<Int> = intPreferencesKey(name = id)
    }

    class FloatSlider(
        id: String,
        defaultValue: Float,
        val min: Float,
        val max: Float,
        val step: Float = 1.0f
    ) : PreferenceKey<Float, Float>(
        id,
        defaultValue,
        serialize = { it },
        deserialize = { it }
    ) {
        override fun getDataStoreKey(): Preferences.Key<Float> = floatPreferencesKey(name = id)
    }

    class Choice<T>(
        id: String,
        defaultValue: T,
        val entries: EnumEntries<T>
    ) : PreferenceKey<T, String>(
        id,
        defaultValue,
        serialize = { it.value },
        deserialize = { value ->
            entries
                .find { entry ->
                    entry.value == value
                } ?: defaultValue
        }
    ) where T : Enum<T>, T : PreferenceEnum {
        override fun getDataStoreKey(): Preferences.Key<String> = stringPreferencesKey(name = id)
    }

}
