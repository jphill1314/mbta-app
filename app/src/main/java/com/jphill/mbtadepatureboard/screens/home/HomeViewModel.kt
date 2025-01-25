package com.jphill.mbtadepatureboard.screens.home

import androidx.lifecycle.viewModelScope
import com.jphill.mbtadepatureboard.LocationCallbacks

import com.jphill.mbtadepatureboard.common.BaseIdentityViewModel
import com.jphill.mbtadepatureboard.data.Prediction
import com.jphill.mbtadepatureboard.data.Route
import com.jphill.mbtadepatureboard.data.Stop
import com.jphill.mbtadepatureboard.network.MBTAService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mbtaService: MBTAService,
) : BaseIdentityViewModel<HomeViewState>(
    initialViewState = HomeViewState(),
), LocationCallbacks {

    override fun onMissingLocationPermission() {
        updateState { copy(showRequestPermissionItem = true) }
    }

    override fun onLocationFetched(latitude: Double, longitude: Double) {
        fetchNearbyStops(latitude, longitude)
    }

    override fun onLocationFetchError() {
        updateState { copy(isNearbyLoading = false) }
    }

    private fun fetchNearbyStops(
        latitude: Double,
        longitude: Double,
    ) {
        viewModelScope.launch {
            updateState {
                copy(
                    isNearbyLoading = true,
                    showRequestPermissionItem = false,
                )
            }
            val closestStops = mbtaService.getStops(
                filterLatitude = latitude.toString(),
                filterLongitude = longitude.toString(),
                filterRadius = 0.01, // This is measured in degrees for some reason so this equates to about 1/2 mile
                filterRouteType = Route.Type.HEAVY_RAIL.toNetwork().toString() + "," + Route.Type.LIGHT_RAIL.toNetwork().toString(),
                sort = "distance",
            )

            val stopNames = mutableMapOf<String, Stop>()
            var stopsToKeep = 0
            closestStops.data.forEachIndexed { index, stop ->
                stopNames[stop.attributes.name] = stop
                if (stopNames.size <= 4) {
                    stopsToKeep = index
                }
            }

            // TODO?: if fewer than 4 subways stops nearby then fetch nearest bus stops

            val stopPredictions = mutableMapOf<String, MutableList<Prediction>>()
            closestStops.data.take(stopsToKeep).forEach { stop ->
                val prediction = mbtaService.getPredictions(
                    filterStop = stop.id,
                    pageLimit = 1,
                )
                stopPredictions.getOrPut(
                    stop.attributes.name,
                    { mutableListOf() }
                ).add(prediction.data.first())
            }

            val format = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            val currentTime = LocalDateTime.now()
            val nearbyStops = stopPredictions.map { entry ->
                NearbyStop(
                    name = entry.key,
                    predictions = entry.value.map { prediction ->
                        val tripData = mbtaService.getTripById(prediction.relationships.trip.data!!.id)
                        val routeData = mbtaService.getRoutes(filterId = prediction.relationships.route.data!!.id)
                        val timeToArrival = prediction.attributes.arrival_time?.let {
                            val parsedDate = LocalDateTime.parse(it, format)
                            val timeToNow = ChronoUnit.MINUTES.between(currentTime, parsedDate)
                            "$timeToNow min"
                        } ?: "Soon"
                        NearbyStopPrediction(
                            destination = tripData.data.attributes.headsign,
                            timeToArrival = timeToArrival,
                            backgroundColor = routeData.data.first().attributes.color,
                            textColor = routeData.data.first().attributes.textColor,
                        )
                    }
                )
            }

            updateState {
                copy(
                    isNearbyLoading = false,
                    showRequestPermissionItem = false,
                    nearbyStops = nearbyStops,
                )
            }
        }
    }
}

data class HomeViewState(
    val showRequestPermissionItem: Boolean = false,
    val isNearbyLoading: Boolean = true,
    val nearbyStops: List<NearbyStop> = emptyList(),
)