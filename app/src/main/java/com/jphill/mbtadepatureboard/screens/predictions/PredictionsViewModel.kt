package com.jphill.mbtadepatureboard.screens.predictions


import androidx.lifecycle.viewModelScope
import com.jphill.mbtadepatureboard.common.BaseIdentityViewModel
import com.jphill.mbtadepatureboard.common.BaseViewModel
import com.jphill.mbtadepatureboard.common.Reducer
import com.jphill.mbtadepatureboard.network.MBTAService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PredictionsViewModel @Inject constructor(
    private val mbtaService: MBTAService,
) : BaseViewModel<PredictionsViewState, PredictionsDataState>(
    initialDataState = PredictionsDataState()
), Reducer<PredictionsViewState, PredictionsDataState> by PredictionsReducer {

    private var refreshJob: Job? = null

    init {
        updateCountdowns()
    }

    private fun updateCountdowns() {
        viewModelScope.launch {
            val thirtySeconds = TimeUnit.SECONDS.toMillis(10)
            withContext(Dispatchers.IO) {
                while (true) {
                    delay(thirtySeconds)
                    updateState { copy(currentTime = LocalDateTime.now()) }
                }
            }
        }
    }

    fun fetchPredictionsForStopAndRoute(
        stopId: String,
        routeId: String,
    ) {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            val refreshPeriod = TimeUnit.MINUTES.toMillis(1)
            withContext(Dispatchers.IO) {
                while (true) {
                    refreshPredictions(stopId, routeId)
                    delay(refreshPeriod)
                }
            }
        }
    }

    private suspend fun refreshPredictions(
        stopId: String,
        routeId: String,
    ) {
        val response = mbtaService.getPredictions(
            pageLimit = 8,
            filterStop = stopId,
            filterRoute = routeId,
        )

        val format = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val allPredictions = response.data.map { prediction ->
            val trip = mbtaService.getTripById(prediction.relationships.trip.data!!.id)
            PredictionData(
                arrivalTime = prediction.attributes.arrival_time?.let { LocalDateTime.parse(it, format) },
                departureTime = prediction.attributes.departure_time?.let { LocalDateTime.parse(it, format) },
                direction = trip.data.attributes.headsign,
            )
        }
        val byDirection = allPredictions.groupBy { it.direction }
        val predictionsList = byDirection.map {
            PredictionByDirection(
                direction = it.key,
                predictions = it.value,
            )
        }

        updateState { copy(predictions = predictionsList) }
    }
}