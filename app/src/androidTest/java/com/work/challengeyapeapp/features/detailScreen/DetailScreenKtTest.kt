package com.work.challengeyapeapp.features.detailScreen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.work.challengeyapeapp.core.common.Failure
import com.work.challengeyapeapp.core.common.ResultType
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.usecase.GetRecipeUseCase
import com.work.challengeyapeapp.ui.theme.CodeChallengeYapeTheme
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var viewModel: DetailScreenViewModel
    private lateinit var navController: TestNavHostController
    private val getRecipeDetailUseCase = mockk<GetRecipeUseCase>()
    private val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)

    @Before
    fun setUp() {

        every { savedStateHandle.get<String>("id") } returns "1"

        val liveDataId = MutableLiveData("1")
        every { savedStateHandle.getLiveData<String>("id") } returns liveDataId

        composeTestRule.activity.runOnUiThread {
            liveDataId.value = "1"
        }
        val fakeRecipe = listOf(
            RecipeModel(
                id = "1",
                name = "Pizza",
                category = "Italian",
                image = "https://example.com/pizza.jpg",
                description = "Deliciosa pizza italiana.",
                location = "Italia",
                ingredients = listOf("Harina", "Tomate", "Queso")
            )
        )
        coEvery { getRecipeDetailUseCase.invoke(any()) } returns flowOf(
            ResultType.Success(
                fakeRecipe
            )
        )

        viewModel =
            DetailScreenViewModel(getRecipeDetailUseCase, savedStateHandle = savedStateHandle)
        navController = TestNavHostController(composeTestRule.activity)

    }

    @Test
    fun testRecipeDetailsAreDisplayed() {
        val fakeRecipe = listOf(
            RecipeModel(
                id = "1",
                name = "Pizza",
                category = "Italian",
                image = "https://example.com/pizza.jpg",
                description = "Deliciosa pizza italiana.",
                location = "Italia",
                ingredients = listOf("Harina", "Tomate", "Queso")
            )
        )

        coEvery { getRecipeDetailUseCase.invoke(any()) } returns flowOf(
            ResultType.Success(
                fakeRecipe
            )
        )

        composeTestRule.setContent {
            CodeChallengeYapeTheme {
                DetailScreen(idRecipe = "1", viewModel = viewModel, navController = navController)
            }
        }

        viewModel.getRecipeDetail("Sushi")

    }

    @Test
    fun testErrorScreen_isDisplayed() {
        coEvery { getRecipeDetailUseCase.invoke(any()) } returns flowOf(ResultType.Error(Failure.UnExpected))

        composeTestRule.setContent {
            CodeChallengeYapeTheme {
                DetailScreen(idRecipe = "1", viewModel = viewModel, navController = navController)
            }
        }
        viewModel.getRecipeDetail("Pizza")
    }
}
