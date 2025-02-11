package com.work.challengeyapeapp.features.homeScreen

import com.work.challengeyapeapp.domain.model.RecipeModel

internal data class HomeScreenState(
    val isLoading: Boolean = false,
    val data: List<RecipeModel>? = null,
    val error: String = ""
)
