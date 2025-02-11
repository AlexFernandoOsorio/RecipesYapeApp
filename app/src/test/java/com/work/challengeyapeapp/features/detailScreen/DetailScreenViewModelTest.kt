package com.work.challengeyapeapp.features.detailScreen

import androidx.lifecycle.SavedStateHandle
import com.work.challengeyapeapp.core.common.ResultType
import com.work.challengeyapeapp.coroutineRule.CoroutineTestRule
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.usecase.GetRecipeUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenViewModelTest {


    @RelaxedMockK
    private lateinit var getRecipeUseCase: GetRecipeUseCase
    @RelaxedMockK
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: DetailScreenViewModel


    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = DetailScreenViewModel(getRecipeUseCase,savedStateHandle)
    }

    @Test
    fun `getRecipeDetail returns FlowResult Success`() = coroutineTestRule.runBlockingTest {
        // Given
        val recipeList = listOf(
            RecipeModel(
                "1234",
                "Meat Recipe",
                "Test Description",
                "Test Image",
                "Test Type",
                emptyList(),
                "Test Ingredients"
            )
        )

        coEvery { getRecipeUseCase("1234") } returns flow {
            emit(ResultType.Success(recipeList))
        }

        // When
        viewModel.getRecipeDetail("1234")

        // Then
        coVerify { getRecipeUseCase("1234") }
    }
}
