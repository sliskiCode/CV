package com.slesarew.mvi

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class ConnectableViewModel<ACTION : Any, STATE : Any>(
    initialState: STATE,
    block: Container<ACTION, STATE>.(STATE) -> Unit
) : ViewModel() {

    private var stateConsumer: (STATE) -> Unit = { states.add(it) }

    private val states = mutableListOf<STATE>()

    var currentState: STATE = initialState

    private val container: Container<ACTION, STATE> =
        Container<ACTION, STATE>().apply { block(currentState) }

    fun sendAction(action: ACTION, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            container.consume(currentState, action, dispatcher) {
                currentState = it
                stateConsumer(currentState)
            }
        }
    }

    fun connect(stateConsumer: (STATE) -> Unit) = connectAndPushInitialState(stateConsumer)

    @VisibleForTesting
    fun testConnect(): List<STATE> = states.also { connectAndPushInitialState() }

    private fun connectAndPushInitialState(stateConsumer: (STATE) -> Unit = {}) {
        this.stateConsumer = {
            states.add(it)
            stateConsumer.invoke(it)
        }
        this.stateConsumer.invoke(currentState)
    }
}
