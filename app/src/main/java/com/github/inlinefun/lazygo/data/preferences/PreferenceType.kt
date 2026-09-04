package com.github.inlinefun.lazygo.data.preferences

import androidx.annotation.StringRes
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlin.enums.EnumEntries

sealed interface PreferenceEnum {
    @get:StringRes
    val label: Int
    val key: String
}

sealed class PreferenceType<A, B>(
    val key: String,
    val defaultValue: A,
) {
    abstract fun serialize(value: A): B
    abstract fun deserialize(value: B): A
    abstract fun getDataStoreKey(): Preferences.Key<B>
    class Switch(
        key: String,
        defaultValue: Boolean
    ) : PreferenceType<Boolean, Boolean>(
        key, defaultValue
    ) {
        override fun serialize(value: Boolean): Boolean = value
        override fun deserialize(value: Boolean): Boolean = value
        override fun getDataStoreKey(): Preferences.Key<Boolean> = booleanPreferencesKey(name = key)
    }

    class IntSlider(
        key: String,
        defaultValue: Int
    ) : PreferenceType<Int, Int>(
        key, defaultValue
    ) {
        override fun serialize(value: Int): Int = value
        override fun deserialize(value: Int): Int = value
        override fun getDataStoreKey(): Preferences.Key<Int> = intPreferencesKey(name = key)
    }

    class FloatSlider(
        key: String,
        defaultValue: Float
    ) : PreferenceType<Float, Float>(
        key, defaultValue
    ) {
        override fun serialize(value: Float): Float = value
        override fun deserialize(value: Float): Float = value
        override fun getDataStoreKey(): Preferences.Key<Float> = floatPreferencesKey(name = key)
    }

    class Choice<T>(
        key: String,
        defaultValue: T,
        val entries: EnumEntries<T>
    ) : PreferenceType<T, String>(
        key, defaultValue
    ) where T : Enum<T>, T : PreferenceEnum {
        override fun serialize(value: T): String {
            return value.key
        }

        override fun deserialize(value: String): T {
            return entries
                .find { entry ->
                    entry.key == value
                } ?: defaultValue
        }

        override fun getDataStoreKey(): Preferences.Key<String> = stringPreferencesKey(name = key)
    }
}
