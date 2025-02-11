package com.work.challengeyapeapp.domain.repositories

import com.work.challengeyapeapp.core.common.Failure
import com.work.challengeyapeapp.core.common.ResultType
import com.work.challengeyapeapp.domain.model.RecipeModel
import kotlinx.coroutines.flow.Flow

internal interface RecipeRepository {

    fun getRecipeListFromApi(name: String): Flow<ResultType<List<RecipeModel>, Failure>>

    fun getRecipeFromApi(id: String): Flow<ResultType<List<RecipeModel>, Failure>>

    suspend fun insertRecipeToLocal(recipe: RecipeModel)
}
