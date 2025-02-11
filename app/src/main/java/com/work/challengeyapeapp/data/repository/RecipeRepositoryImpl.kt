package com.work.challengeyapeapp.data.repository

import com.work.challengeyapeapp.core.common.Failure
import com.work.challengeyapeapp.core.common.ResultType
import com.work.challengeyapeapp.data.local.dao.RecipeDao
import com.work.challengeyapeapp.data.mapper.toModel
import com.work.challengeyapeapp.data.mapper.toRecipeEntity
import com.work.challengeyapeapp.data.remote.services.RecipesAPi
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

internal class RecipeRepositoryImpl @Inject constructor(
    private val recipesApi: RecipesAPi,
    private val recipeDao: RecipeDao
) : RecipeRepository {

    override fun getRecipeListFromApi(name: String): Flow<ResultType<List<RecipeModel>, Failure>> = flow {
        try {
            val recipeList = recipesApi.getRecipesListByLetter(name)
            val recipeModelList = recipeList.toModel()
            emit(ResultType.Success(recipeModelList))
        } catch (e: IOException) {
            emit(ResultType.Error(Failure.NetworkConnection))
        } catch (e: UnknownHostException){
            emit(ResultType.Error(Failure.Http))
        } catch (t : Throwable){
            emit(ResultType.Error(Failure.UnExpected))
        }
    }

    override fun getRecipeFromApi(value: String): Flow<ResultType<List<RecipeModel>, Failure>> = flow {
        try {
            val recipeById = recipesApi.getRecipesById(value)
            val recipeByIdModel = recipeById.toModel()
            emit(ResultType.Success(recipeByIdModel))
        } catch (e: IOException) {
            emit(ResultType.Error(Failure.NetworkConnection))
        } catch (e: UnknownHostException){
            emit(ResultType.Error(Failure.Http))
        } catch (t : Throwable){
            emit(ResultType.Error(Failure.UnExpected))
        }
    }

    override suspend fun insertRecipeToLocal(recipe: RecipeModel) {
        recipeDao.insertRecipe(recipe.toRecipeEntity())
    }
}
