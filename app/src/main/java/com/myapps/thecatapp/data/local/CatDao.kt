package com.myapps.thecatapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.myapps.thecatapp.data.local.model.CatEntity

@Dao
interface CatDao {

    @Upsert
    suspend fun upsertCat(cats: CatEntity)
    @Update
    suspend fun updateCat(cat: CatEntity)
    @Upsert
    suspend fun upsertCats(cats: List<CatEntity>)
    @Query("SELECT * FROM cats WHERE imageId = :imageId LIMIT 1")
    suspend fun getCatByImageId(imageId: String): CatEntity?
    @Query("SELECT * FROM cats LIMIT :limit OFFSET :offset")
    suspend fun getCats(limit: Int = 20, offset: Int): List<CatEntity>
    @Query("SELECT * FROM cats WHERE isFavourite = 1")
    suspend fun getFavourites(): List<CatEntity>
    @Query("SELECT * FROM cats WHERE name LIKE '%' || :breed || '%'")
    suspend fun searchBreeds(breed: String): List<CatEntity>
}