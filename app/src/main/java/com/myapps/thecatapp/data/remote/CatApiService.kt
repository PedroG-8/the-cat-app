package com.myapps.thecatapp.data.remote

import com.myapps.thecatapp.data.model.CatDto
import com.myapps.thecatapp.data.model.FavouriteDto
import com.myapps.thecatapp.data.model.FavouriteRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CatApiService {
    @GET("v1/images/search")
    suspend fun getCatBreeds(
        @Query("has_breeds") hasBreeds: Boolean = true,
        @Query("order") order: String = "ASC",
        @Query("limit") limit: Int = 21,
        @Query("page") page: Int = 0
    ): List<CatDto>

    @GET("v1/favourites")
    suspend fun getFavourites(): List<FavouriteDto>

    @POST("v1/favourites")
    suspend fun addToFavourites(
        @Body request: FavouriteRequest
    ): Response<Unit>

    @DELETE("v1/favourites/{favourite_id}")
    suspend fun removeFromFavourites(
        @Path("favourite_id") favouriteId: String
    ): Response<Unit>
}