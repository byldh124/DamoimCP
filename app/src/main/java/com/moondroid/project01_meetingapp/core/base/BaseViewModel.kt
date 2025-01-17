package com.moondroid.project01_meetingapp.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface UiState
interface UiEvent
interface UiEffect

abstract class BaseViewModel<S : UiState, E : UiEvent, F : UiEffect>(initialState: S) : ViewModel() {

    private val _effect = Channel<F>(Channel.UNLIMITED)
    val effect: Flow<F> = _effect.receiveAsFlow()

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    val event = Channel<E>(Channel.UNLIMITED)

    private val currentState: S
        get() = _uiState.value

    init {
        viewModelScope.launch {
            event.receiveAsFlow().collect {
                handleEvent(it)
            }
        }
    }

    protected abstract suspend fun handleEvent(event: E)

    protected fun setState(reduce: S.() -> S) {
        val state = currentState.reduce()
        _uiState.value = state
    }

    protected fun setEffect(effect: F) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}