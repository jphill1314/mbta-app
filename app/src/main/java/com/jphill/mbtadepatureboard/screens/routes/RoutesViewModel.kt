package com.jphill.mbtadepatureboard.screens.routes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jphill.mbtadepatureboard.data.Route
import com.jphill.mbtadepatureboard.data.toLocal
import com.jphill.mbtadepatureboard.network.MBTAService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel @Inject constructor(
    private val mbtaService: MBTAService,
) : ViewModel() {

    private val state = MutableStateFlow(RoutesViewState())
    val viewState = state.asStateFlow()

    fun fetchRoutes(types: List<Int>) {
        viewModelScope.launch {
            val response = mbtaService.getRoutes(
                filterType = types,
            )
            val routes = response.data.map { it.toLocal() }
            state.update {
                it.copy(
                    routes = routes,
                )
            }
        }
    }
}

data class RoutesViewState(
    val routes: List<Route> = emptyList(),
)