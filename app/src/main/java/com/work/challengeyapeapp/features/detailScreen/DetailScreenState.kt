package com.work.challengeyapeapp.features.detailScreen

import com.work.challengeyapeapp.domain.model.RecipeModel

internal data class DetailScreenState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<RecipeModel>? = null
)
