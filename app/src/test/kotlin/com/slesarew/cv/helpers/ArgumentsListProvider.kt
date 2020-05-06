package com.slesarew.cv.helpers

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

abstract class ArgumentsListProvider(private val arguments: Iterable<*>) : ArgumentsProvider {

    constructor(vararg argument: Any?) : this(argument.toList())

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
        arguments.map { arguments(it) }.stream()
}
