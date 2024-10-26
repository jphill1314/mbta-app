package com.jphill.mbtadepatureboard.data

import android.os.Parcelable
import com.jphill.mbtadepatureboard.data.Route.Type.BUS
import com.jphill.mbtadepatureboard.data.Route.Type.COMMUTER_RAIL
import com.jphill.mbtadepatureboard.data.Route.Type.FERRY
import com.jphill.mbtadepatureboard.data.Route.Type.HEAVY_RAIL
import com.jphill.mbtadepatureboard.data.Route.Type.LIGHT_RAIL
import kotlinx.parcelize.Parcelize

data class Route(
    val id: String,
    val lineId: String,
    val color: String,
    val description: String,
    val type: Type,
    val directionDestinations: List<String>,
    val directionNames: List<String>,
    val fareClass: String,
    val longName: String,
    val shortName: String,
    val textColor: String,
) {
    val name = if (type == BUS) {
        "$shortName - $longName"
    } else {
        longName
    }

    enum class Type {
        LIGHT_RAIL,
        HEAVY_RAIL,
        COMMUTER_RAIL,
        BUS,
        FERRY,
        ;

        fun toNetwork() = when (this) {
            LIGHT_RAIL -> 0
            HEAVY_RAIL -> 1
            COMMUTER_RAIL -> 2
            BUS -> 3
            FERRY -> 4
        }
    }
}

fun Int.toRouteType() = when (this) {
    0 -> LIGHT_RAIL
    1 -> HEAVY_RAIL
    2 -> COMMUTER_RAIL
    3 -> BUS
    4 -> FERRY
    else -> throw IllegalArgumentException("Route type value must be 0-4. Got $this")
}

fun RouteResponse.toLocal() = Route(
    id = id,
    lineId = relationships.line.data.id,
    color = attributes.color,
    description = attributes.description,
    type = attributes.type.toRouteType(),
    directionDestinations = attributes.directionDestinations,
    directionNames = attributes.directionNames,
    fareClass = attributes.fareClass,
    longName = attributes.longName,
    shortName = attributes.shortName,
    textColor = attributes.textColor,
)

