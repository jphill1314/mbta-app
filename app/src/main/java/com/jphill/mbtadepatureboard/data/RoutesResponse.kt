package com.jphill.mbtadepatureboard.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RoutesResponse(
    val data: List<RouteResponse>,
)

@JsonClass(generateAdapter = true)
data class RouteResponse(
    val id: String,
    val type: String,
    val attributes: Attributes,
    val relationships: Relationships,
) {
    @JsonClass(generateAdapter = true)
    data class Attributes(
        val color: String,
        val description: String,
        val type: Int,

        @Json(name = "direction_destinations")
        val directionDestinations: List<String>,

        @Json(name = "direction_names")
        val directionNames: List<String>,

        @Json(name = "fare_class")
        val fareClass: String,

        @Json(name = "long_name")
        val longName: String,

        @Json(name = "short_name")
        val shortName: String,

        @Json(name = "text_color")
        val textColor: String,
    )

    @JsonClass(generateAdapter = true)
    data class Relationships(
        val line: Line,
    )

    @JsonClass(generateAdapter = true)
    data class Line(
        val data: Data,
    ) {
        @JsonClass(generateAdapter = true)
        data class Data(
            val id: String,
            val type: String,
        )
    }
}