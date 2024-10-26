package com.jphill.mbtadepatureboard.network

import com.jphill.mbtadepatureboard.data.Line
import com.jphill.mbtadepatureboard.data.LinesResponse
import com.jphill.mbtadepatureboard.data.RoutesResponse
import com.jphill.mbtadepatureboard.data.StopsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MBTAService {

    @GET("lines/")
    suspend fun getLines(
        @Query("page[offset]") pageOffset: Int? = null,
        @Query("page[limit]") pageLimit: Int? = null,
    ): LinesResponse

    @GET("lines/{line_id}")
    suspend fun getLineById(
        @Path("line_id") lineId: String,
    ): Line

    @GET("routes/")
    suspend fun getRoutes(
        @Query("page[offset]") pageOffset: Int? = null,
        @Query("page[limit]") pageLimit: Int? = null,
        @Query("filter[type]") filterType: List<Int>? = null,
    ): RoutesResponse

    @GET("stops/")
    suspend fun getStops(
        @Query("filter[route]") filterRoute: String? = null,
    ): StopsResponse
}