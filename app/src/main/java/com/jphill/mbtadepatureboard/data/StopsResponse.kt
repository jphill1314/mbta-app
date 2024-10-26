package com.jphill.mbtadepatureboard.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StopsResponse(
    val data: List<Stop>,
)

@JsonClass(generateAdapter = true)
data class Stop(
    val id: String,
    val attributes: Attributes,
) {

    @JsonClass(generateAdapter = true)
    data class Attributes(
        val address: String?,
        val at_street: String?,
        val description: String?,
        val latitude: Double,
        val location_type: Int,
        val longitude: Double,
        val municipality: String,
        val name: String,
        val on_street: String?,
        val platform_code: String?,
        val platform_name: String?,
        val vehicle_type: Int?,
        val wheelchair_boarding: Int,
    )
}