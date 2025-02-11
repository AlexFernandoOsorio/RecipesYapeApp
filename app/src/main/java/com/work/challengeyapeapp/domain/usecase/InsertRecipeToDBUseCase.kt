package com.work.challengeyapeapp.domain.usecase

import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import javax.inject.Inject

internal class InsertRecipeToDBUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(recipe: RecipeModel) {
        recipeRepository.insertRecipeToLocal(recipe)
    }
}
