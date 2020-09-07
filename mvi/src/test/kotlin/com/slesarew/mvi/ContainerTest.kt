package com.slesarew.mvi

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.slesarew.mvi.TestAction.GoTo
import com.slesarew.mvi.TestAction.GoTo2
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

sealed class TestAction {
    object GoTo : TestAction()
    object GoTo2 : TestAction()
}

data class TestState(val id: String = "")

class ContainerTest {

    private val tested = Container<TestAction, TestState>()

    @Test
    fun `transforms action to status`() {
        val status = "1337"
        val expected = TestState("1337")

        tested.intentionOn(action = GoTo::class) {
            transform = { status }
            reduce = { status, state ->
                require(status is String)

                state.copy(
                    id = status
                )
            }
        }

        runBlocking {
            tested.consume(TestState(), GoTo) { actual ->
                assertThat(actual).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `does not reduce state when reduction not defined`() {
        val status = "1337"
        val initialState = TestState()

        tested.intentionOn(action = GoTo::class) {
            transform = { status }
        }

        runBlocking {
            tested.consume(initialState, GoTo) { actual ->
                assertThat(actual).isSameAs(initialState)
            }
        }
    }

    @Test
    fun `does not transform unsupported action`() {
        runBlocking {
            val runCatching = runCatching {
                tested.consume(TestState(), GoTo2) { }
            }

            assertThat(runCatching.isFailure).isTrue()
        }
    }

    @Test
    fun `runs action as side effect`() {
        val function = mock<(TestState, TestAction) -> Unit>()
        tested.intentionOn(action = GoTo::class) {
            sideEffect = function
        }

        runBlocking {
            tested.consume(TestState(), GoTo) {}
        }

        verify(function).invoke(TestState(), GoTo)
    }

    @Test
    fun `sends loopback action back to the container`() {
        val status = "1337"
        val expected = TestState("1337")

        tested.intentionOn(action = GoTo::class) {
            transform = { status }
            reduce = { status, state ->
                require(status is String)

                state.copy(
                    id = status
                )
            }
        }

        val function = mock<(TestState, TestAction) -> Unit>()
        tested.intentionOn(action = GoTo2::class) {
            sideEffect = function
            loopBack = GoTo
        }

        runBlocking {
            tested.consume(TestState(), GoTo2) { actual ->
                assertThat(actual).isEqualTo(expected)
                verify(function).invoke(TestState(), GoTo2)
            }
        }
    }
}
