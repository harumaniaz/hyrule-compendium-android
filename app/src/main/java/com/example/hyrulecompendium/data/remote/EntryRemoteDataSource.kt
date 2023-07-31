package com.example.hyrulecompendium.data.remote

import com.example.hyrulecompendium.data.Entry
import com.example.hyrulecompendium.data.GameType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EntryRemoteDataSource(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val apiService: ApiService
) {
    suspend fun getAllEntries(
        type: GameType = GameType.BOTW
    ): List<Entry> = withContext(defaultDispatcher) {
        apiService.getAllEntries(game = type.id).data
    }
}