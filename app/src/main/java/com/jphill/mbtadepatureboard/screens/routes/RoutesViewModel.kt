package com.jphill.mbtadepatureboard.screens.routes

import androidx.lifecycle.viewModelScope
import com.jphill.mbtadepatureboard.common.BaseIdentityViewModel
import com.jphill.mbtadepatureboard.data.Route
import com.jphill.mbtadepatureboard.data.toLocal
import com.jphill.mbtadepatureboard.network.MBTAService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel @Inject constructor(
    private val mbtaService: MBTAService,
) : BaseIdentityViewModel<RoutesViewState>(initialViewState = RoutesViewState()) {

    fun fetchRoutes(types: List<Int>) {
        viewModelScope.launch {
            val response = mbtaService.getRoutes(
                filterType = buildFilterString(types),
            )
            val routes = response.data.map { it.toLocal() }
            updateState { copy(routes = routes) }
        }
    }

    private fun buildFilterString(types: List<Int>): String {
        return types.joinToString(
            separator = ",",
            transform = { it.toString() },
        )
    }
}

data class RoutesViewState(
    val routes: List<Route> = emptyList(),
)