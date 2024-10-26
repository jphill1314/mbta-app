package com.jphill.mbtadepatureboard.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TripResponse(
    val data: Trip,
)

@JsonClass(generateAdapter = true)
data class Trip(
    val id: String,
    val attributes: Attributes,
) {

    @JsonClass(generateAdapter = true)
    data class Attributes(
        val bikes_allowed: Int,
        val block_id: String?,
        val headsign: String,
        val name: String,
        val revenue: String,
        val wheelchair_accessible: Int,
    )
}