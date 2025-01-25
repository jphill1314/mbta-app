package com.jphill.mbtadepatureboard.network

import com.jphill.mbtadepatureboard.data.Line
import com.jphill.mbtadepatureboard.data.LinesResponse
import com.jphill.mbtadepatureboard.data.PredictionsResponse
import com.jphill.mbtadepatureboard.data.RoutesResponse
import com.jphill.mbtadepatureboard.data.StopsResponse
import com.jphill.mbtadepatureboard.data.TripResponse
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
        @Query("filter[type]") filterType: String? = null,
        @Query("filter[id]") filterId: String? = null,
    ): RoutesResponse

    @GET("stops/")
    suspend fun getStops(
        @Query("filter[route]") filterRoute: String? = null,
        @Query("filter[route_type]") filterRouteType: String? = null,
        @Query("filter[latitude]") filterLatitude: String? = null,
        @Query("filter[longitude]") filterLongitude: String? = null,
        @Query("filter[radius]") filterRadius: Double? = null,
        @Query("sort") sort: String? = null,
        @Query("page[limit]") pageLimit: Int? = null,
    ): StopsResponse

    @GET("predictions/")
    suspend fun getPredictions(
        @Query("page[offset]") pageOffset: Int? = null,
        @Query("page[limit]") pageLimit: Int? = null,
        @Query("filter[direction_id]") filterDirectionId: String? = null,
        @Query("filter[stop]") filterStop: String? = null,
        @Query("filter[route]") filterRoute: String? = null,
    ): PredictionsResponse

    @GET("trips/{trip_id}")
    suspend fun getTripById(
        @Path("trip_id") tripId: String,
    ): TripResponse
}