package com.slesarew.cv.repository.service

import com.slesarew.cv.repository.model.CVData
import retrofit2.http.GET

interface GithubService {

    @GET("CVData.json")
    suspend fun getCVData(): CVData

    companion object {

        const val BASE_URL =
            "https://raw.githubusercontent.com/sliskiCode/sliskiCode.github.io/master/"
    }
}
