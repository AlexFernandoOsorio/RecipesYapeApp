package com.work.challengeyapeapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.work.challengeyapeapp.core.common.Failure
import com.work.challengeyapeapp.core.common.ResultType
import com.work.challengeyapeapp.coroutineRule.CoroutineTestRule
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GetRecipeListUseCaseTest {

    @RelaxedMockK
    private lateinit var recipeRepository: RecipeRepository

    private lateinit var getRecipeListUseCase: GetRecipeListUseCase

    private val expected = listOf(
        RecipeModel(
            "1",
            "Pork",
            "Pork description",
            "Pork image",
            "Pork type",
            emptyList(),
            "Pork ingredients"
        )
    )


    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getRecipeListUseCase = GetRecipeListUseCase(recipeRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given I obtain recipe list from API, When I try to obtain it on use case, Then I verify if was called on repository`() =
        coroutineTestRule.runBlockingTest {
            // Given
            coEvery { recipeRepository.getRecipeListFromApi(any()) } returns flow {
                emit(ResultType.Success(expected))
            }

            // When
            val value = mutableListOf<ResultType<List<RecipeModel>, Failure>>()
            getRecipeListUseCase("pork").collect {
                value.add(it)
            }

            // Then
            coVerify { recipeRepository.getRecipeListFromApi("pork") }
            assert(value.first() is ResultType.Success)
        }
}
