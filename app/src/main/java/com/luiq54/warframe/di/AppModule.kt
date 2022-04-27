package com.luiq54.warframe.di

import com.luiq54.warframe.data.remote.WarframeApi
import com.luiq54.warframe.repository.WarframeRepository
import com.luiq54.warframe.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesWarframeRepository(
        api: WarframeApi
    ) = WarframeRepository(api)


    @Singleton
    @Provides
    fun providesWarframeApi() : WarframeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(WarframeApi::class.java)

    }
}