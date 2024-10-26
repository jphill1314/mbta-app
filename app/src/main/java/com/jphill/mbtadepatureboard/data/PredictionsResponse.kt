package com.jphill.mbtadepatureboard.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PredictionsResponse(
    val data: List<Prediction>,
)

@JsonClass(generateAdapter = true)
data class Prediction(
    val id: String,
    val attributes: Attributes,
    val relationships: Relationships,
) {

    @JsonClass(generateAdapter = true)
    data class Attributes(
        val arrival_time: String?,
        val arrival_uncertainty: Int?,
        val departure_time: String?,
        val departure_uncertainty: Int?,
        val direction_id: Int,
        val last_trip: Boolean,
        val revenue: String,
        val schedule_relationship: String?,
        val status: String?,
        val stop_sequence: Int,
        val update_type: String?,
    )

    @JsonClass(generateAdapter = true)
    data class Relationships(
        val route: RelationData,
        val stop: RelationData,
        val trip: RelationData,
        val vehicle: RelationData,
        val alerts: RelationData?,
    )

    @JsonClass(generateAdapter = true)
    data class RelationData(
        val data: Data?,
    ) {
        @JsonClass(generateAdapter = true)
        data class Data(val id: String)
    }
}