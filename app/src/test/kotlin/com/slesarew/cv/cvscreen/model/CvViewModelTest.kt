package com.slesarew.cv.cvscreen.model

import com.google.common.truth.Truth.assertThat
import com.google.gson.JsonSyntaxException
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.slesarew.cv.R
import com.slesarew.cv.cvscreen.model.CvAction.OnMediumClickedAction
import com.slesarew.cv.cvscreen.model.CvAction.OnScreenCreateAction
import com.slesarew.cv.cvscreen.model.CvAction.OnStackOverflowClickedAction
import com.slesarew.cv.cvscreen.model.CvAction.OnYouTubeClickedAction
import com.slesarew.cv.helpers.ArgumentsListProvider
import com.slesarew.cv.helpers.CoroutineExtension
import com.slesarew.cv.repository.model.CVData
import com.slesarew.cv.repository.model.Position
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
@ExtendWith(CoroutineExtension::class)
class CvViewModelTest {

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
        val tested = CvViewModel(transformerWithData(data), CvReducers(), sideEffects())
        val states = tested.testConnect()

        tested.sendAction(OnScreenCreateAction, Dispatchers.Unconfined)

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
        val tested = CvViewModel(transformerWithError(error), CvReducers(), sideEffects())
        val states = tested.testConnect()

        tested.sendAction(OnScreenCreateAction, Dispatchers.Unconfined)

        assertThat(states[0]).isEqualTo(CVState())
        assertThat(states[1]).isEqualTo(
            CVState(
                status = Status.Error("Error")
            )
        )
    }

    @Test
    fun `navigates to medium page`() {
        val navigateToMediumPage = mock<(CVState, CvAction) -> Unit>()
        val sideEffects = sideEffects(navigateToMediumPage = navigateToMediumPage)
        val tested = CvViewModel(transformerWithData(data), CvReducers(), sideEffects)

        tested.sendAction(OnMediumClickedAction, Dispatchers.Unconfined)

        verify(navigateToMediumPage).invoke(CVState(), OnMediumClickedAction)
    }

    @Test
    fun `navigates to stack overflow page`() {
        val navigateToStackOverflow = mock<(CVState, CvAction) -> Unit>()
        val sideEffects = sideEffects(navigateToStackOverflow = navigateToStackOverflow)
        val tested = CvViewModel(transformerWithData(data), CvReducers(), sideEffects)

        tested.sendAction(OnStackOverflowClickedAction, Dispatchers.Unconfined)

        verify(navigateToStackOverflow).invoke(CVState(), OnStackOverflowClickedAction)
    }

    @Test
    fun `navigates to you tube page`() {
        val navigateToYouTube = mock<(CVState, CvAction) -> Unit>()
        val sideEffects = sideEffects(navigateToYouTube = navigateToYouTube)
        val tested = CvViewModel(transformerWithData(data), CvReducers(), sideEffects)

        tested.sendAction(OnYouTubeClickedAction, Dispatchers.Unconfined)

        verify(navigateToYouTube).invoke(CVState(), OnYouTubeClickedAction)
    }
}

private fun transformerWithData(data: CVData) = CvTransformers(
    mock { onBlocking { fetchCVData() }.thenReturn(data) },
    mock()
)

private fun transformerWithError(throwable: Throwable) = CvTransformers(
    mock { onBlocking { fetchCVData() }.thenAnswer { throw throwable } },
    mock {
        on { getString(R.string.unknown_host_message) }.thenReturn("Error")
        on { getString(R.string.server_issue_message) }.thenReturn("Error")
    }
)

private fun sideEffects(
    navigateToMediumPage: (CVState, CvAction) -> Unit = { _, _ -> },
    navigateToStackOverflow: (CVState, CvAction) -> Unit = { _, _ -> },
    navigateToYouTube: (CVState, CvAction) -> Unit = { _, _ -> }
) = mock<CvSideEffects> {
    on { navigateToMediumPage() }.thenReturn(navigateToMediumPage)
    on { navigateToStackOverflow() }.thenReturn(navigateToStackOverflow)
    on { navigateToYouTube() }.thenReturn(navigateToYouTube)
}

class ErrorProvider : ArgumentsListProvider(
    UnknownHostException(),
    JsonSyntaxException("Error")
)
