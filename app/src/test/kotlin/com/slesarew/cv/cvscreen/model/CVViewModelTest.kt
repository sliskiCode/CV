package com.slesarew.cv.cvscreen.model

import com.google.common.truth.Truth.assertThat
import com.google.gson.JsonSyntaxException
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.slesarew.cv.R
import com.slesarew.cv.cvscreen.model.CVAction.*
import com.slesarew.cv.helpers.ArgumentsListProvider
import com.slesarew.cv.helpers.CoroutineExtension
import com.slesarew.cv.repository.model.CVData
import com.slesarew.cv.repository.model.Position
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
@ExtendWith(CoroutineExtension::class)
class CVViewModelTest {

    private val data = CVData(
        name = "Jake",
        surname = "Wharton",
        email = "jake.wharton@nomoreatgoogle.com",
        phoneNumber = "137-137-1337",
        photoUrl = "www.1337.com/photo.png",
        summary = "10x engineer",
        positions = listOf(
            Position(
                title = "Godfather",
                dates = "Since the beginning of Android till now",
                description = "Doing great things",
                technologies = "All of them"
            )
        ),
        personalDevelopment = listOf(
            com.slesarew.cv.repository.model.PersonalDevelopment(
                description = "Being Jake"
            )
        ),
        hobbies = "programming, being a nerd",
        mediumUrl = "https://medium.com/@jakewharton",
        stackOverflowUrl = "https://stackoverflow.com/users/132047/jake-wharton",
        youtubeUrl = "https://www.youtube.com/results?search_query=jake+wharton"
    )

    @Test
    fun `loads data on OnScreenCreateAction`() {
        val tested = CVViewModel(transformerWithData(data), CVReducers(), sideEffects())
        val states = tested.connectInTest()

        tested.sendAction(OnScreenCreateAction)

        assertThat(states[0]).isEqualTo(CVState())
        assertThat(states[1]).isEqualTo(
            CVState(
                status = Status.Ready,
                headerData = HeaderData(
                    name = "Jake Wharton",
                    email = "jake.wharton@nomoreatgoogle.com",
                    phoneNumber = "137-137-1337",
                    photoUrl = "www.1337.com/photo.png"
                ),
                summaryData = SummaryData(
                    summary = "10x engineer"
                ),
                jobPositions = listOf(
                    JobPosition(
                        title = "Godfather",
                        dates = "Since the beginning of Android till now",
                        description = "Doing great things",
                        technologies = "All of them"
                    )
                ),
                personalDevelopment = PersonalDevelopment(
                    descriptions = listOf(
                        "Being Jake"
                    )
                ),
                hobbiesData = HobbiesData(
                    hobbies = "programming, being a nerd"
                ),
                links = LinksData(
                    mediumUrl = "https://medium.com/@jakewharton",
                    stackOverflowUrl = "https://stackoverflow.com/users/132047/jake-wharton",
                    youtubeUrl = "https://www.youtube.com/results?search_query=jake+wharton"
                )
            )
        )
    }

    @ParameterizedTest
    @ArgumentsSource(ErrorProvider::class)
    fun `produces error state`(error: Throwable) {
        val tested = CVViewModel(transformerWithError(error), CVReducers(), sideEffects())
        val states = tested.connectInTest()

        tested.sendAction(OnScreenCreateAction)

        assertThat(states[0]).isEqualTo(CVState())
        assertThat(states[1]).isEqualTo(
            CVState(
                status = Status.Error("Error")
            )
        )
    }

    @Test
    fun `navigates to medium page`() {
        val navigateToMediumPage = mock<(CVState) -> Unit>()
        val sideEffects = sideEffects(navigateToMediumPage = navigateToMediumPage)
        val tested = CVViewModel(transformerWithData(data), CVReducers(), sideEffects)

        tested.sendAction(OnMediumClickedAction)

        verify(navigateToMediumPage).invoke(CVState())
    }

    @Test
    fun `navigates to stack overflow page`() {
        val navigateToStackOverflow = mock<(CVState) -> Unit>()
        val sideEffects = sideEffects(navigateToStackOverflow = navigateToStackOverflow)
        val tested = CVViewModel(transformerWithData(data), CVReducers(), sideEffects)

        tested.sendAction(OnStackOverflowClickedAction)

        verify(navigateToStackOverflow).invoke(CVState())
    }

    @Test
    fun `navigates to you tube page`() {
        val navigateToYouTube = mock<(CVState) -> Unit>()
        val sideEffects = sideEffects(navigateToYouTube = navigateToYouTube)
        val tested = CVViewModel(transformerWithData(data), CVReducers(), sideEffects)

        tested.sendAction(OnYouTubeClickedAction)

        verify(navigateToYouTube).invoke(CVState())
    }
}

private fun transformerWithData(data: CVData) = CVTransformers(
    mock { onBlocking { fetchCVData() }.thenReturn(data) },
    mock()
)

private fun transformerWithError(throwable: Throwable) = CVTransformers(
    mock { onBlocking { fetchCVData() }.thenAnswer { throw throwable } },
    mock {
        on { getString(R.string.unknown_host_message) }.thenReturn("Error")
        on { getString(R.string.server_issue_message) }.thenReturn("Error")
    }
)

private fun sideEffects(
    navigateToMediumPage: (CVState) -> Unit = {},
    navigateToStackOverflow: (CVState) -> Unit = {},
    navigateToYouTube: (CVState) -> Unit = {}
) = mock<CVSideEffects> {
    on { navigateToMediumPage() }.thenReturn(navigateToMediumPage)
    on { navigateToStackOverflow() }.thenReturn(navigateToStackOverflow)
    on { navigateToYouTube() }.thenReturn(navigateToYouTube)
}

class ErrorProvider : ArgumentsListProvider(
    UnknownHostException(),
    JsonSyntaxException("Error")
)
