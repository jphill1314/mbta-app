package com.jphill.mbtadepatureboard.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseIdentityViewModel<VS>(
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

abstract class BaseViewModel<VS, DS>(
    initialDataState: DS,
) : ViewModel() {

    private val dataState = MutableStateFlow(initialDataState)
    val viewState = dataState.asStateFlow()
        .mapState(::reduce)

    internal fun updateState(updateBlock: DS.() -> DS) {
        dataState.update {
            it.updateBlock()
        }
    }

    abstract fun reduce(dataState: DS): VS
}