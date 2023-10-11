package com.example.asdemoapp.data.remote

import com.example.asdemoapp.data.remote.entity.PublicApisResponseEntity
import retrofit2.http.GET
import retrofit2.Response

interface PublicApisApi {
    @GET("entries")
    suspend fun getApiCategories(): Response<PublicApisResponseEntity>

    companion object {
        const val BASE_URL = "https://api.publicapis.org/"
    }
}