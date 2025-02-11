package com.work.challengeyapeapp.features.homeScreen

import com.work.challengeyapeapp.core.common.ResultType
import com.work.challengeyapeapp.coroutineRule.CoroutineTestRule
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.usecase.GetRecipeListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenViewModelTest {

    @RelaxedMockK
    private lateinit var getRecipeListUseCase: GetRecipeListUseCase

    private lateinit var viewModel: HomeScreenViewModel

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = HomeScreenViewModel(getRecipeListUseCase)
    }

    @Test
    fun `getRecipesList returns FlowResult Success`() = coroutineTestRule.runBlockingTest {
        // Given
        val recipeList = listOf(
            RecipeModel(
                "1",
                "Meat Recipe",
                "Test Description",
                "Test Image",
                "Test Type",
                emptyList(),
                "Test Ingredients"
            )
        )
        coEvery { getRecipeListUseCase("meat") } returns flow {
            emit(ResultType.Success(recipeList))
        }

        viewModel.getRecipesList("meat")

        // Then
        coVerify { getRecipeListUseCase("meat") }
    }
}
