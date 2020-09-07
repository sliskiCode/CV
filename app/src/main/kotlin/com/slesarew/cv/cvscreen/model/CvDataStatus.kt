package com.slesarew.cv.cvscreen.model

import com.slesarew.cv.repository.model.CVData

sealed class CvDataStatus {

    data class Ready(val cvData: CVData) : CvDataStatus()
    data class Error(val message: String) : CvDataStatus()
}
