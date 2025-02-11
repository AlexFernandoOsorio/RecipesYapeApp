package com.work.challengeyapeapp.data.repository

import com.work.challengeyapeapp.core.common.Failure
import com.work.challengeyapeapp.core.common.ResultType
import com.work.challengeyapeapp.coroutineRule.CoroutineTestRule
import com.work.challengeyapeapp.data.local.dao.RecipeDao
import com.work.challengeyapeapp.data.mapper.toRecipeEntity
import com.work.challengeyapeapp.data.remote.response.RecipeDto
import com.work.challengeyapeapp.data.remote.response.RecipeListResponse
import com.work.challengeyapeapp.data.remote.services.RecipesAPi
import com.work.challengeyapeapp.domain.model.RecipeModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RecipeRepositoryImplTest {

    @MockK
    private lateinit var recipesApi: RecipesAPi

    @MockK
    private lateinit var recipeDao: RecipeDao

    private lateinit var recipeRepositoryImpl: RecipeRepositoryImpl

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        recipeRepositoryImpl = RecipeRepositoryImpl(recipesApi, recipeDao)
    }

    @Test
    fun `when getRecipeListFromApi is called then verify it was called on RecipesApi`() =
        coroutineTestRule.runBlockingTest {
            // Given
            val expectedResponse = RecipeListResponse(
                listOf(
                    RecipeDto(
                        "1",
                        "Pork",
                        "Pork description",
                        "Pork image",
                        "Pork type",
                        "emptyList",
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
                    )
                )
            )

            coEvery { recipesApi.getRecipesListByLetter("pork") } returns expectedResponse

            // When
            val value = mutableListOf<ResultType<List<RecipeModel>, Failure>>()
            recipeRepositoryImpl.getRecipeListFromApi("pork").collect {
                value.add(it)
            }

            // Then
            coVerify { recipesApi.getRecipesListByLetter("pork") }
            assert(value.first() is ResultType.Success)
        }

    @Test
    fun `when getRecipeFromApi is called then return success`() =
        coroutineTestRule.runBlockingTest {
            // Given
            val expectedResponse = RecipeListResponse(emptyList())

            coEvery { recipesApi.getRecipesById("1") } returns expectedResponse

            // When
            val value = mutableListOf<ResultType<List<RecipeModel>, Failure>>()
            recipeRepositoryImpl.getRecipeFromApi("1").collect {
                value.add(it)
            }

            // Then
            coVerify { recipesApi.getRecipesById("1") }
            assert(value.first() is ResultType.Success)
    }

    @Test
    fun `when insertRecipeToLocal is called then verify it was called on RecipeDao`() =
        coroutineTestRule.runBlockingTest {
            // Given
            val recipe = RecipeModel(
                "1",
                "Pork",
                "Pork description",
                "Pork image",
                "Pork type",
                emptyList(),
                "Pork ingredients"
            )
            coEvery { recipeDao.insertRecipe(recipe.toRecipeEntity()) } returns Unit

            // When
            recipeRepositoryImpl.insertRecipeToLocal(recipe)

            // Then
            coVerify { recipeDao.insertRecipe(recipe.toRecipeEntity()) }
        }
}
