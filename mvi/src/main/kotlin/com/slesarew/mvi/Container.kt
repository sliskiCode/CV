package com.slesarew.mvi

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.reflect.KClass

class Container<ACTION : Any, STATE : Any> {

    private val transformations =
        mutableMapOf<KClass<out ACTION>, IntentionTransformation<ACTION, Any, STATE>>()

    private val sideEffects =
        mutableMapOf<KClass<out ACTION>, IntentionSideEffect<STATE>>()

    fun intentionOn(
        action: KClass<out ACTION>,
        block: IntentionTransformation<ACTION, Any, STATE>.() -> Unit
    ) {
        transformations[action] = IntentionTransformation<ACTION, Any, STATE>().apply(block)
    }

    fun sideEffectIntentionOn(
        action: KClass<out ACTION>,
        block: IntentionSideEffect<STATE>.() -> Unit
    ) {
        sideEffects[action] = IntentionSideEffect<STATE>().apply(block)
    }

    suspend fun consume(
        state: STATE,
        action: ACTION,
        stateConsumer: (STATE) -> Unit
    ) {
        val intentionSideEffect = sideEffects[action::class]
        if (intentionSideEffect != null) {
            intentionSideEffect.sideEffect(state)
        } else {
            coroutineScope {
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
    }
}

class IntentionTransformation<ACTION : Any, STATUS : Any, STATE : Any> {
    lateinit var transform: suspend (ACTION) -> STATUS
    var reduce: suspend ((STATUS, STATE) -> STATE) = { _, state -> state }
}

class IntentionSideEffect<STATE : Any> {
    var sideEffect: (STATE) -> Unit = {}
}
