package com.slesarew.mvi

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.reflect.KClass

class Container<ACTION : Any, STATE : Any> {

    private val transformations =
        mutableMapOf<KClass<out ACTION>, Intention<ACTION, Any, STATE>>()

    fun intentionOn(
        action: KClass<out ACTION>,
        block: Intention<ACTION, Any, STATE>.() -> Unit
    ) {
        transformations[action] = Intention<ACTION, Any, STATE>().apply(block)
    }

    suspend fun consume(
        state: STATE,
        action: ACTION,
        stateConsumer: (STATE) -> Unit
    ) = coroutineScope {
        val transformation = transformations[action::class]?.also { it.sideEffect(state) }

        transformation ?: throw IllegalStateException(
            "Action not supported. Please define intention using: intentionOn(action = ${action::class.simpleName}:class)"
        )

        val reductionState = async {
            val status = transformation.transform(action)
            status?.let { transformation.reduce(status, state) } ?: state
        }

        stateConsumer(reductionState.await())
    }
}
