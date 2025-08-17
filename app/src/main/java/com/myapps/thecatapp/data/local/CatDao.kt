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
    suspend fun updateCat(cats: CatEntity)
    @Upsert
    suspend fun upsertCats(cats: List<CatEntity>)
    @Query("SELECT * FROM cats WHERE imageId = :imageId LIMIT 1")
    suspend fun getCatByImageId(imageId: String): CatEntity?
    @Query("SELECT * FROM cats LIMIT :limit OFFSET :offset")
    suspend fun getCats(limit: Int = 20, offset: Int): List<CatEntity>
}