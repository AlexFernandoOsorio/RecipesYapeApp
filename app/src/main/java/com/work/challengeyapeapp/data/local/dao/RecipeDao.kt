package com.work.challengeyapeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.work.challengeyapeapp.data.local.entities.RecipeEntity

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe ORDER BY tr_id DESC")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipe WHERE tr_name LIKE :name")
    suspend fun getRecipeListByName(name: String): List<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)
}
