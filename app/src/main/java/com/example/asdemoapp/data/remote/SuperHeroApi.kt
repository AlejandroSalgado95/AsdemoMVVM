package com.example.asdemoapp.data.remote

import com.example.asdemoapp.data.remote.entity.SuperHeroEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperHeroApi {
    @GET("id/{superhero_id}.json")
    suspend fun getSuperHero (@Path(value = "superhero_id", encoded = true) superHeroId : String): Response<SuperHeroEntity>

    companion object {
        const val BASE_URL = "https://akabab.github.io/superhero-api/api/"
    }
}