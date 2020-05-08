package com.slesarew.cv.repository.model

import com.google.gson.annotations.SerializedName

data class CVData(
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("e_mail") val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("photo_url") val photoUrl: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("positions") val positions: List<Position>,
    @SerializedName("personal_development") val personalDevelopment: List<PersonalDevelopment>,
    @SerializedName("hobbies") val hobbies: String,
    @SerializedName("medium_url") val mediumUrl: String,
    @SerializedName("stack_overflow_url") val stackOverflowUrl: String,
    @SerializedName("you_tube_url") val youtubeUrl: String
)