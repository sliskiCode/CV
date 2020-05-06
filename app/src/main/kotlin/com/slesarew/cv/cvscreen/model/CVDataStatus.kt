package com.slesarew.cv.cvscreen.model

import com.slesarew.cv.repository.model.CVData

sealed class CVDataStatus {

    data class Ready(val cvData: CVData): CVDataStatus()
    data class Error(val message: String): CVDataStatus()
}