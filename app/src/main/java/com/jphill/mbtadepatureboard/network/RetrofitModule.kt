package com.jphill.mbtadepatureboard.network

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun providesMoshiConverterFactory(): MoshiConverterFactory {
//        val moshi = Moshi.Builder().addLast()
        return MoshiConverterFactory.create()
    }

    @Provides
    fun provideRetrofit(
        converterFactory: MoshiConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-v3.mbta.com/")
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideMBTAService(retrofit: Retrofit) = retrofit.create(MBTAService::class.java)
}