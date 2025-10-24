package com.nhuhuy.mythos.creatures.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface CreatureApi {
    @GET("creatures")
    suspend fun fetchCreatureList(): List<CreatureDTO>

    @GET("creatures/{id}")
    suspend fun getCreatureDetail(
        @Path("id") id: Int
    ): CreatureDTO

}