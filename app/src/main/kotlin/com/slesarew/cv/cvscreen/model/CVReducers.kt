package com.slesarew.cv.cvscreen.model

import com.slesarew.cv.repository.model.Position

typealias Reduction = suspend (Any, CVState) -> CVState

class CVReducers {

    fun reduceCVData(): Reduction = { status, state ->
        when (status) {
            is CVDataStatus.Ready -> status.cvData.let { data ->
                state.copy(
                    status = Status.Ready,
                    headerData = state.headerData.copy(
                        name = "${data.name} ${data.surname}",
                        email = data.email,
                        phoneNumber = data.phoneNumber,
                        photoUrl = data.photoUrl
                    ),
                    summaryData = state.summaryData.copy(
                        summary = data.summary.toString()
                    ),
                    jobPositions = data.positions.map { it.toPresentationModel() },
                    personalDevelopment = state.personalDevelopment.copy(
                        descriptions = data.personalDevelopment.map { it.description }
                    ),
                    hobbiesData = state.hobbiesData.copy(
                        hobbies = status.cvData.hobbies
                    )
                )
            }
            is CVDataStatus.Error -> state.copy(
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