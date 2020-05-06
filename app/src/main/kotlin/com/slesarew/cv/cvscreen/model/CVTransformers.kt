package com.slesarew.cv.cvscreen.model

import android.content.Context
import com.google.gson.JsonSyntaxException
import com.slesarew.cv.R
import com.slesarew.cv.cvscreen.model.CVDataStatus.Error
import com.slesarew.cv.cvscreen.model.CVDataStatus.Ready
import com.slesarew.cv.repository.CVRepository
import java.net.UnknownHostException

typealias Transformation = suspend (CVAction) -> CVDataStatus

class CVTransformers(
    private val repository: CVRepository,
    private val context: Context
) {

    fun loadCVData(): Transformation = { action ->
        require(action is CVAction.OnScreenCreateAction)

        try {
            Ready(repository.fetchCVData())
        } catch (e: UnknownHostException) {
            Error(context.getString(R.string.unknown_host_message))
        } catch (e: JsonSyntaxException) {
            Error(context.getString(R.string.json_syntax_message))
        }
    }
}