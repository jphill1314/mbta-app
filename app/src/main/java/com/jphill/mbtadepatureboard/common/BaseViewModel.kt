package com.jphill.mbtadepatureboard.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<VS>(
    initialViewState: VS
) : ViewModel() {

    private val state = MutableStateFlow(initialViewState)
    val viewState = state.asStateFlow()

    internal fun updateState(updateBlock: VS.() -> VS) {
        state.update {
            it.updateBlock()
        }
    }
}