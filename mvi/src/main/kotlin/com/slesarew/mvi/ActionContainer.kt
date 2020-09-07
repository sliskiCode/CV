package com.slesarew.mvi

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class ActionContainer<ACTION : Any, STATE : Any>(
    initialState: STATE,
    block: Container<ACTION, STATE>.(STATE) -> Unit
) {

    private var stateConsumer: (STATE) -> Unit = { states.add(it) }

    private val states = mutableListOf<STATE>()

    var currentState: STATE = initialState

    private val container: Container<ACTION, STATE> =
        Container<ACTION, STATE>().apply { block(currentState) }

    fun sendAction(action: ACTION) {
        GlobalScope.launch {
            container.consume(currentState, action) {
                currentState = it
                stateConsumer(currentState)
            }
        }
    }
}
