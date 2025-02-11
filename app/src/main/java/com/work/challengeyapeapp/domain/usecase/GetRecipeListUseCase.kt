package com.work.challengeyapeapp.domain.usecase

import com.work.challengeyapeapp.core.common.Failure
import com.work.challengeyapeapp.core.common.ResultType
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetRecipeListUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(param: String) : Flow<ResultType<List<RecipeModel>, Failure>> =
        recipeRepository.getRecipeListFromApi(param).map {
            when (it) {
                is ResultType.Success -> {
                    if (it.value.isNotEmpty()) {
                        ResultType.Success(it.value)
                    } else {
                        ResultType.Error(Failure.Http)
                    }
                }
                is ResultType.Error -> ResultType.Error(it.value)
            }
        }
}
