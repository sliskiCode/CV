package com.slesarew.mvi

typealias Transform<ACTION, STATUS> = suspend (ACTION) -> STATUS
typealias Reduce<STATUS, STATE> = suspend ((STATUS, STATE) -> STATE)
typealias SideEffect<STATE, ACTION> = (STATE, ACTION) -> Unit

class Intention<ACTION : Any, STATUS : Any, STATE : Any> {
    var transform: Transform<ACTION, STATUS>? = null
    var reduce: Reduce<STATUS, STATE>? = null
    var sideEffect: SideEffect<STATE, ACTION>? = null
    var loopBack: ACTION? = null
}
