package com.work.challengeyapeapp.di

import com.work.challengeyapeapp.data.local.dao.RecipeDao
import com.work.challengeyapeapp.data.remote.services.RecipesAPi
import com.work.challengeyapeapp.data.repository.RecipeRepositoryImpl
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal object RepositoriesModule {

    @Provides
    fun provideRecipeRepository(recipesApi: RecipesAPi, recipeDao: RecipeDao): RecipeRepository {
        return RecipeRepositoryImpl(recipesApi, recipeDao)
    }
}
