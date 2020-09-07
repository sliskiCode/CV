package com.slesarew.mvi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

class Container<ACTION : Any, STATE : Any> {

    private val intentions =
        mutableMapOf<KClass<out ACTION>, Intention<ACTION, Any, STATE>>()

    fun intentionOn(
        action: KClass<out ACTION>,
        block: Intention<ACTION, Any, STATE>.() -> Unit
    ) {
        intentions[action] = Intention<ACTION, Any, STATE>().apply(block)
    }

    fun intentionOn(
        actions: Array<KClass<out ACTION>>,
        block: Intention<ACTION, Any, STATE>.() -> Unit
    ) {
        val intention = Intention<ACTION, Any, STATE>().apply(block)
        actions.forEach { intentions[it] = intention }
    }

    suspend fun consume(
        state: STATE,
        action: ACTION,
        stateConsumer: (STATE) -> Unit
    ) {
        val intention = requireNotNull(intentions[action::class]) {
            "Action not supported. Please define intention using: intentionOn(action = ${action::class.simpleName}:class)"
        }

        val reducedState = withContext(Dispatchers.IO) {
            val transformedStatus = intention.transform?.invoke(action)

            if (transformedStatus == null) {
                intention.reduce?.invoke(action, state)
            } else {
                intention.reduce?.invoke(transformedStatus, state)
            }
        }

        reducedState?.let { stateConsumer(it) }

        intention.sideEffect?.invoke(reducedState ?: state, action)

        intention.loopBack?.let { loopBackAction ->
            consume(
                state = reducedState ?: state,
                action = loopBackAction,
                stateConsumer = stateConsumer
            )
        }
    }
}
