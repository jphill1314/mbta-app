package com.jphill.mbtadepatureboard.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LinesResponse(
    val data: List<Line>,
)

@JsonClass(generateAdapter = true)
data class Line(
    val id: String,
    val type: String,
    val attributes: Attributes,
) {

    val name = if (attributes.shortName.isNotBlank()) {
        attributes.longName + " - " + attributes.shortName
    } else {
        attributes.longName
    }

    @JsonClass(generateAdapter = true)
    data class Attributes(
        val color: String,

        @Json(name = "long_name")
        val longName: String,

        @Json(name = "short_name")
        val shortName: String,

        @Json(name = "sort_order")
        val sortOrder: Int,

        @Json(name = "text_color")
        val textColor: String,
    )
}