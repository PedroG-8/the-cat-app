package com.myapps.thecatapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("v1/images/search")
    suspend fun getCatBreeds(
        @Query("has_breeds") hasBreeds: Boolean = true,
        @Query("order") order: String = "ASC",
        @Query("limit") limit: Int = 15,
        @Query("page") page: Int = 0
    ): List<CatDto>
}