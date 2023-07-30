package com.example.hyrulecompendium.data.repository

import com.example.hyrulecompendium.data.GameType
import com.example.hyrulecompendium.data.remote.ApiError
import com.example.hyrulecompendium.data.remote.ApiException
import com.example.hyrulecompendium.data.remote.ApiResult
import com.example.hyrulecompendium.data.remote.ApiService
import com.example.hyrulecompendium.data.remote.ApiSuccess
import com.example.hyrulecompendium.data.remote.EntriesData
import com.example.hyrulecompendium.data.remote.Entry
import com.example.hyrulecompendium.data.remote.EntryData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ApiRepository(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val apiService: ApiService
) {
    private var allEntries: EntriesData = EntriesData(data = emptyList()) // Cached data

    private suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>,
        cache: (T) -> Unit = {}
    ): ApiResult<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                cache(body)
                ApiSuccess(body)
            } else {
                ApiError(code = response.code(), message = response.message())
            }
        } catch (e: Exception) {
            ApiException(exception = e)
        }
    }

    suspend fun getAllEntries(type: GameType = GameType.BOTW): ApiResult<EntriesData> =
        withContext(defaultDispatcher) {
            return@withContext handleApi(
                execute = { apiService.getAllEntries(game = type.id) },
                cache = { allEntries = it }
            )
        }

    fun getAllEntriesInCache(): List<Entry> = allEntries.data

    suspend fun getEntry(id: Int, type: GameType = GameType.BOTW): ApiResult<EntryData> =
        withContext(defaultDispatcher) {
            return@withContext handleApi(
                execute = {
                    apiService.getEntry(
                        id = id,
                        game = type.id
                    )
                })
        }

    fun getEntryInCache(id: Int): Entry? = allEntries.data.firstOrNull { it.id == id }
}