package com.jphill.mbtadepatureboard.screens.stops

import androidx.lifecycle.viewModelScope
import com.jphill.mbtadepatureboard.common.BaseIdentityViewModel
import com.jphill.mbtadepatureboard.data.Stop
import com.jphill.mbtadepatureboard.network.MBTAService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopsViewModel @Inject constructor(
    private val mbtaService: MBTAService,
) : BaseIdentityViewModel<StopsViewState>(initialViewState = StopsViewState()) {

    fun fetchStopsForRoute(routeId: String) {
        viewModelScope.launch {
            val response = mbtaService.getStops(filterRoute = routeId)
            updateState { copy(stops = response.data) }
        }
    }
}

data class StopsViewState(
    val stops: List<Stop> = emptyList(),
)