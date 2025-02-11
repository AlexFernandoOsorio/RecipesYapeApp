package com.work.challengeyapeapp.data.mapper

import com.work.challengeyapeapp.data.local.entities.RecipeEntity
import com.work.challengeyapeapp.data.remote.response.RecipeDto
import com.work.challengeyapeapp.data.remote.response.RecipeListResponse
import com.work.challengeyapeapp.domain.model.RecipeModel

internal fun RecipeListResponse.toModel(): List<RecipeModel> {
    return this.meals.map {
        RecipeModel(
            id = it.idMeal ?: "",
            name = it.strMeal ?: "",
            description = it.strInstructions ?: "",
            category = it.strCategory ?: "",
            location = it.strArea ?: "",
            ingredients = extractIngredients(it),
            image = it.strMealThumb ?: ""
        )
    }
}

private fun extractIngredients(it: RecipeDto): List<String> {
    return listOf(
        it.strIngredient1 ?: "",
        it.strIngredient2 ?: "",
        it.strIngredient3 ?: "",
        it.strIngredient4 ?: "",
        it.strIngredient5 ?: "",
        it.strIngredient6 ?: "",
        it.strIngredient7 ?: "",
        it.strIngredient8 ?: "",
        it.strIngredient9 ?: "",
        it.strIngredient10 ?: "",
        it.strIngredient11 ?: "",
        it.strIngredient12 ?: "",
        it.strIngredient13 ?: "",
        it.strIngredient14 ?: "",
        it.strIngredient15 ?: "",
        it.strIngredient16 ?: "",
        it.strIngredient17 ?: "",
        it.strIngredient18 ?: "",
        it.strIngredient19 ?: "",
        it.strIngredient20 ?: ""
    )
}

fun RecipeModel.toRecipeEntity(): RecipeEntity {
    return RecipeEntity(
        id = this.id.toInt(),
        name = this.name,
        description = this.description,
        category = this.category,
        location = this.location,
        ingredients = this.ingredients,
        image = this.image
    )
}
