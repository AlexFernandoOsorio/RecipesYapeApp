package com.work.challengeyapeapp.data.remote.services

import com.work.challengeyapeapp.BuildConfig
import com.work.challengeyapeapp.data.remote.response.RecipeListResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RecipesAPi {

    @GET(BuildConfig.END_POINT_RECIPE_ID)
    suspend fun getRecipesById(@Query("i") recipeId: String): RecipeListResponse

    @GET(BuildConfig.END_POINT_RECIPE_NAME)
    suspend fun getRecipesListByLetter(@Query("s") recipeName: String): RecipeListResponse

}
