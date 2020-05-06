package com.slesarew.mvi

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.reflect.KClass

class Container<ACTION : Any, STATE : Any> {

    private val transformations =
        mutableMapOf<KClass<out ACTION>, IntentionTransformation<ACTION, Any, STATE>>()

    fun intentionOn(
        action: KClass<out ACTION>,
        block: IntentionTransformation<ACTION, Any, STATE>.() -> Unit
    ) {
        transformations[action] = IntentionTransformation<ACTION, Any, STATE>().apply(block)
    }

    suspend fun consume(
        state: STATE,
        action: ACTION,
        stateConsumer: (STATE) -> Unit
    ) = coroutineScope {
        val reductionState = async {
            val transformedState: STATE? = transformations[action::class]?.let {
                val transformationStatus = it.transform(action)
                it.reduce(transformationStatus, state)
            }

            transformedState
                ?: throw IllegalStateException(
                    "Action not supported. Please declare transformation using intentionOn(action = ${action::class.simpleName}:class)"
                )
        }

        stateConsumer(reductionState.await())
    }
}

class IntentionTransformation<ACTION : Any, STATUS : Any, STATE : Any> {
    lateinit var transform: suspend (ACTION) -> STATUS
    var reduce: suspend ((STATUS, STATE) -> STATE) = { _, state -> state }
}
