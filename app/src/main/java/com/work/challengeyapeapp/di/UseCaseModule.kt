package com.work.challengeyapeapp.di

import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import com.work.challengeyapeapp.domain.usecase.GetRecipeListUseCase
import com.work.challengeyapeapp.domain.usecase.GetRecipeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal object UseCaseModule {

    @Provides
    fun provideGetRecipeListFromApiUseCase(repository: RecipeRepository): GetRecipeListUseCase {
        return GetRecipeListUseCase(repository)
    }

    @Provides
    fun provideGetRecipeFromApiUseCase(repository: RecipeRepository): GetRecipeUseCase {
        return GetRecipeUseCase(repository)
    }
}
