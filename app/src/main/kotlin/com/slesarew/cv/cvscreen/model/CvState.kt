package com.slesarew.cv.cvscreen.model

data class CVState(
    val status: Status = Status.Loading,
    val headerData: HeaderData = HeaderData(),
    val summaryData: SummaryData = SummaryData(),
    val jobPositions: List<JobPosition> = emptyList(),
    val personalDevelopment: PersonalDevelopment = PersonalDevelopment(),
    val hobbiesData: HobbiesData = HobbiesData(),
    val links: LinksData = LinksData()
)

data class HeaderData(
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val photoUrl: String = ""
)

data class SummaryData(
    val summary: String = ""
)

data class JobPosition(
    val title: String,
    val dates: String,
    val description: String,
    val technologies: String
)

data class PersonalDevelopment(
    val descriptions: List<String> = emptyList()
)

data class HobbiesData(
    val hobbies: String = ""
)

data class LinksData(
    val mediumUrl: String = "",
    val stackOverflowUrl: String = "",
    val youtubeUrl: String = ""
)

sealed class Status {
    object Ready : Status()
    object Loading : Status()
    data class Error(val message: String) : Status()
}
