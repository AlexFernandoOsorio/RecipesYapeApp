package com.work.challengeyapeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.work.challengeyapeapp.data.local.converters.ListStringTypeConverter
import com.work.challengeyapeapp.data.local.dao.RecipeDao
import com.work.challengeyapeapp.data.local.entities.RecipeEntity

@TypeConverters(ListStringTypeConverter::class)
@Database(
    entities = [RecipeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
}
