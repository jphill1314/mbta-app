package com.jphill.mbtadepatureboard.network

import com.jphill.mbtadepatureboard.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentHeaders = chain.request().headers()
        val updatedHeaders = currentHeaders.newBuilder()
            .add("x-api-key", BuildConfig.MBTA_API_KEY)
            .build()
        val updatedRequest = chain.request().newBuilder()
            .headers(updatedHeaders)
            .build()
        return chain.proceed(updatedRequest)

    }
}