package com.example.hyrulecompendium.data.repository

import com.example.hyrulecompendium.data.Entry
import com.example.hyrulecompendium.data.GameType
import com.example.hyrulecompendium.data.remote.EntryRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class EntryRepository(
    private val remoteDataSource: EntryRemoteDataSource
) {
    private val entriesMutex = Mutex()
    private var cachedEntries: List<Entry> = emptyList()
    private var cachedType: GameType? = null

    suspend fun getAllEntries(
        type: GameType
    ): List<Entry> {
        if (cachedEntries.isEmpty() || type != cachedType) {
            val networkResult = remoteDataSource.getAllEntries(type)
            entriesMutex.withLock {
                cachedEntries = networkResult
                cachedType = type
            }
        }
        return entriesMutex.withLock { cachedEntries }
    }

    suspend fun getEntry(id: Int): Entry? =
        entriesMutex.withLock { cachedEntries }.firstOrNull { it.id == id }
}