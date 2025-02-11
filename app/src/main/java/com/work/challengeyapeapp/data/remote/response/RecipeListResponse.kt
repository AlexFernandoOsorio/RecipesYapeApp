package com.work.challengeyapeapp.data.remote.response

import com.google.gson.annotations.SerializedName

internal data class RecipeListResponse(
    @SerializedName("meals")
    val meals: List<RecipeDto>
)
