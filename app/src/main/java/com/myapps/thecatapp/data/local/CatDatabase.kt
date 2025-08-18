package com.myapps.thecatapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myapps.thecatapp.data.local.model.CatEntity

@Database(
    entities = [CatEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CatDatabase: RoomDatabase() {
    abstract val catDao: CatDao
}