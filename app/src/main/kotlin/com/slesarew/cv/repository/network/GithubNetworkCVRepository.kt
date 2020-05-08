package com.slesarew.cv.repository.network

import com.slesarew.cv.repository.CVRepository
import com.slesarew.cv.repository.model.CVData
import com.slesarew.cv.repository.service.GithubService

class GithubNetworkCVRepository(private val service: GithubService) : CVRepository {

    override suspend fun fetchCVData(): CVData = service.getCVData()
}
