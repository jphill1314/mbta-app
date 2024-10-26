package com.jphill.mbtadepatureboard.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun providesMoshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Provides
    fun providesOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()
    }

    @Provides
    fun provideRetrofit(
        converterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-v3.mbta.com/")
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideMBTAService(retrofit: Retrofit) = retrofit.create(MBTAService::class.java)
}