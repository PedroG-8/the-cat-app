package com.myapps.thecatapp.data.remote

import retrofit2.http.GET

interface CatApiService {
    @GET("v1/images/search")
    suspend fun getRandomCat(): List<CatDto>
}