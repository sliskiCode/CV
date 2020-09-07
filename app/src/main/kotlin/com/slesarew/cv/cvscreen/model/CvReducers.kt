package com.slesarew.cv.cvscreen.model

import com.slesarew.cv.repository.model.Position
import com.slesarew.mvi.Reduce

class CvReducers {

    fun reduceCVData(): Reduce<Any, CVState> = { status, state ->
        when (status) {
            is CvDataStatus.Ready -> status.cvData.let { data ->
                state.copy(
                    status = Status.Ready,
                    headerData = state.headerData.copy(
                        name = "${data.name} ${data.surname}",
                        email = data.email,
                        phoneNumber = data.phoneNumber,
                        photoUrl = data.photoUrl
                    ),
                    summaryData = state.summaryData.copy(
                        summary = data.summary
                    ),
                    jobPositions = data.positions.map { it.toPresentationModel() },
                    personalDevelopment = state.personalDevelopment.copy(
                        descriptions = data.personalDevelopment.map { it.description }
                    ),
                    hobbiesData = state.hobbiesData.copy(
                        hobbies = status.cvData.hobbies
                    ),
                    links = state.links.copy(
                        mediumUrl = status.cvData.mediumUrl,
                        stackOverflowUrl = status.cvData.stackOverflowUrl,
                        youtubeUrl = status.cvData.youtubeUrl
                    )
                )
            }
            is CvDataStatus.Error -> state.copy(
                status = Status.Error(status.message)
            )
            else -> state
        }
    }

    private fun Position.toPresentationModel() = JobPosition(
        title = title,
        dates = dates,
        description = description,
        technologies = technologies
    )
}
