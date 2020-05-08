package com.slesarew.cv.repository.network

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.willReturn
import com.slesarew.cv.repository.model.CVData
import com.slesarew.cv.repository.service.GithubService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class GithubNetworkCVRepositoryTest {

    private val mockGithubService = mock<GithubService>()

    private val tested = GithubNetworkCVRepository(mockGithubService)

    @Test
    fun `returns correct data`() {
        val data = mock<CVData>()
        givenBlocking {
            mockGithubService.getCVData()
        } willReturn {
            data
        }

        val actual = runBlocking { tested.fetchCVData() }

        assertThat(actual).isSameAs(data)
    }

    private fun <T> givenBlocking(methodCall: suspend () -> T) = runBlocking { given(methodCall()) }
}
