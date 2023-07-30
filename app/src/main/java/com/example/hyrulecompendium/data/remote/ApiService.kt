package com.example.hyrulecompendium.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/api/v3/compendium/all")
    suspend fun getAllEntries(@Query("game") game: Int): Response<EntriesData>

    @GET("/api/v3/compendium/entry/{id}")
    suspend fun getEntry(@Path("id") id: Int, @Query("game") game: Int): Response<EntryData>
}