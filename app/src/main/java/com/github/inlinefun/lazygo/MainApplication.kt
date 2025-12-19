package com.github.inlinefun.lazygo

import android.app.Application
import androidx.datastore.preferences.preferencesDataStore
import com.github.inlinefun.lazygo.data.PreferencesStore

class MainApplication: Application() {
    val preferencesDataStore by preferencesDataStore("preferences")
    val preferencesStore by lazy {
        PreferencesStore(preferencesDataStore)
    }
}