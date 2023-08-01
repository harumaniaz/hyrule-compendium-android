package com.example.hyrulecompendium.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.hyrulecompendium.data.GameType
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "user_preferences"
)

class DataStoreManager(context: Context) {
    companion object {
        val GAME_TYPE = intPreferencesKey("game_type")
    }

    private val dataStore = context.dataStore

    fun getGameType() = dataStore.data.map { preferences ->
        preferences[GAME_TYPE]?.let {
            GameType.from(id = it)
        } ?: GameType.BOTW
    }

    suspend fun saveGameType(type: GameType) {
        dataStore.edit {
            it[GAME_TYPE] = type.id
        }
    }
}
