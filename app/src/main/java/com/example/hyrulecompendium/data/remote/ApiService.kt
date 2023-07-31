package com.example.hyrulecompendium.data.remote

import com.example.hyrulecompendium.data.EntriesDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/v3/compendium/all")
    suspend fun getAllEntries(@Query("game") game: Int): EntriesDataResponse
}