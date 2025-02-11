package com.work.challengeyapeapp.di

import com.work.challengeyapeapp.core.common.GlobalConstants
import com.work.challengeyapeapp.data.remote.services.RecipesAPi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GlobalConstants.CONST_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideRecipesApi(retrofit: Retrofit): RecipesAPi {
        return provideRetrofit().create(RecipesAPi::class.java)
    }
}
