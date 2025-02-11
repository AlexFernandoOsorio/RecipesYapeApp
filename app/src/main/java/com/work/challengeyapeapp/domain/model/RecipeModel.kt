package com.work.challengeyapeapp.domain.model

data class RecipeModel(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val location: String,
    val ingredients: List<String>,
    val image: String
)
