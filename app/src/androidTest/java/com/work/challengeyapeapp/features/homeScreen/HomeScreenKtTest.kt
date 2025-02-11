package com.work.challengeyapeapp.features.homeScreen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.work.challengeyapeapp.core.common.Failure
import com.work.challengeyapeapp.core.common.ResultType
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.usecase.GetRecipeListUseCase
import com.work.challengeyapeapp.ui.theme.CodeChallengeYapeTheme
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var navController: TestNavHostController
    private val getRecipeListUseCase = mockk<GetRecipeListUseCase>()

    @Before
    fun setUp() {
        coEvery { getRecipeListUseCase.invoke(any()) } returns flowOf(ResultType.Success(emptyList()))

        viewModel = HomeScreenViewModel(getRecipeListUseCase)
        navController = TestNavHostController(composeTestRule.activity)
    }


    @Test
    fun testRecipeList_isDisplayed() {
        val fakeRecipes = listOf(
            RecipeModel(id = "1", name = "Pizza", category = "Italian", image = "https://example.com/pizza.jpg", description = "", location = "", ingredients = emptyList()),
            RecipeModel(id = "2", name = "Sushi", category = "Japanese", image = "https://example.com/sushi.jpg", description = "", location = "", ingredients = emptyList())
        )

        coEvery { getRecipeListUseCase.invoke(any()) } returns flowOf(ResultType.Success(fakeRecipes))

        composeTestRule.setContent {
            CodeChallengeYapeTheme {
                HomeScreen(viewModel, navController)
            }
        }

        viewModel.getRecipesList("")

        composeTestRule.onNodeWithText("Pizza").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sushi").assertIsDisplayed()
    }

    @Test
    fun testErrorScreen_isDisplayed() {
        coEvery { getRecipeListUseCase.invoke(any()) } returns flowOf(ResultType.Error(Failure.Http))

        composeTestRule.setContent {
            CodeChallengeYapeTheme {
                HomeScreen(viewModel, navController)
            }
        }

        viewModel.getRecipesList("")
    }
}