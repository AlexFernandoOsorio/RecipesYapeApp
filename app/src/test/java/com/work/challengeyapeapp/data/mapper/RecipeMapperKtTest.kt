package com.work.challengeyapeapp.data.mapper

import com.work.challengeyapeapp.data.local.entities.RecipeEntity
import com.work.challengeyapeapp.data.remote.response.RecipeDto
import com.work.challengeyapeapp.data.remote.response.RecipeListResponse
import com.work.challengeyapeapp.domain.model.RecipeModel
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test


class RecipeMapperKtTest{
    @Test
    fun `when toRecipeEntity is called then verify it returns correct RecipeEntity`() {
        // Given
        val model = RecipeModel(
            id = "1",
            name = "Pork",
            description = "Pork description",
            category = "Pork type",
            location = "emptyList",
            ingredients = listOf(
                "Pork ingredients",
                "Pork instructions",
                "Pork source",
                "Pork tags",
                "Pork webUrl",
                "Pork youtubeUrl",
                "Pork duration",
                "Pork difficulty",
                "Pork dishType",
                "Pork cuisineType",
                "Pork mealType",
                "Pork calories",
                "Pork protein",
                "Pork fat",
                "Pork carbs",
                "Pork fiber",
                "Pork sugar",
                "Pork saturatedFat",
                "Pork cholesterol",
                "Pork sodium",
                "Pork potassium",
                "Pork vitaminA",
                "Pork vitaminC",
                "Pork calcium",
                "Pork iron",
                "Pork magnesium",
                "Pork phosphorus",
                "Pork zinc",
                "Pork thiamin",
                "Pork riboflavin",
                "Pork niacin",
                "Pork vitaminB6",
                "Pork folate",
                "Pork vitaminB12",
                "Pork vitaminD",
                "Pork vitaminE",
                "Pork vitaminK",
                "Pork caffeine",
                "Pork water",
                "Pork alcohol",
                "Pork caffeine",
                "Pork water",
                "Pork alcohol",
                "Pork caffeine",
                "Pork water",
                "Pork alcohol",
                "Pork caffeine"
            ),
            image = "Pork image"
        )
        val expected = RecipeEntity(
            id = 1,
            name = "Pork",
            description = "Pork description",
            category = "Pork type",
            location = "emptyList",
            ingredients = listOf(
                "Pork ingredients",
                "Pork instructions",
                "Pork source",
                "Pork tags",
                "Pork webUrl",
                "Pork youtubeUrl",
                "Pork duration",
                "Pork difficulty",
                "Pork dishType",
                "Pork cuisineType",
                "Pork mealType",
                "Pork calories",
                "Pork protein",
                "Pork fat",
                "Pork carbs",
                "Pork fiber",
                "Pork sugar",
                "Pork saturatedFat",
                "Pork cholesterol",
                "Pork sodium",
                "Pork potassium",
                "Pork vitaminA",
                "Pork vitaminC",
                "Pork calcium",
                "Pork iron",
                "Pork magnesium",
                "Pork phosphorus",
                "Pork zinc",
                "Pork thiamin",
                "Pork riboflavin",
                "Pork niacin",
                "Pork vitaminB6",
                "Pork folate",
                "Pork vitaminB12",
                "Pork vitaminD",
                "Pork vitaminE",
                "Pork vitaminK",
                "Pork caffeine",
                "Pork water",
                "Pork alcohol",
                "Pork caffeine",
                "Pork water",
                "Pork alcohol",
                "Pork caffeine",
                "Pork water",
                "Pork alcohol",
                "Pork caffeine"
            ),
            image = "Pork image"
        )

        // When
        val result = model.toRecipeEntity()

        // Then
        Assert.assertEquals(expected, result)
    }
}
