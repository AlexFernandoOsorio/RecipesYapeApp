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

@ExperimentalCoroutinesApi
class GetRecipeUseCaseTest {

    @RelaxedMockK
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var getRecipeUseCase: GetRecipeUseCase

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getRecipeUseCase = GetRecipeUseCase(recipeRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when invoke is called then verify it was called on RecipeRepository`() =
        coroutineTestRule.runBlockingTest {
            // Given
            val param = "1"
            val expected = listOf(
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
            coEvery { recipeRepository.getRecipeFromApi(param) } returns flow {
                emit(ResultType.Success(expected))
            }

            // When
            val value = mutableListOf<ResultType<List<RecipeModel>, Failure>>()
            getRecipeUseCase(param).collect {
                value.add(it)
            }

            // Then
            coVerify { recipeRepository.getRecipeFromApi(param) }
            assert(value.first() is ResultType.Success)
        }
}
