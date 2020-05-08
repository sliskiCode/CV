package com.slesarew.cv.repository

import com.slesarew.cv.repository.model.CVData

interface CVRepository {

    suspend fun fetchCVData(): CVData
}
