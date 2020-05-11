package com.slesarew.cv.cvscreen.model

import android.content.Context
import com.slesarew.cv.R
import com.slesarew.cv.cvscreen.model.CVDataStatus.Error
import com.slesarew.cv.cvscreen.model.CVDataStatus.Ready
import com.slesarew.cv.repository.CVRepository
import com.slesarew.mvi.Transform
import java.net.UnknownHostException

class CVTransformers(
    private val repository: CVRepository,
    private val context: Context
) {

    @Suppress("TooGenericExceptionCaught")
    fun loadCVData(): Transform<CVAction, CVDataStatus> = { action ->
        require(action is CVAction.OnScreenCreateAction)

        try {
            Ready(repository.fetchCVData())
        } catch (e: UnknownHostException) {
            Error(context.getString(R.string.unknown_host_message))
        } catch (e: Exception) {
            Error(context.getString(R.string.server_issue_message))
        }
    }
}
