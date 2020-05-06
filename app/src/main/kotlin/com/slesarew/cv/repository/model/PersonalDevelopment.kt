package com.slesarew.cv.repository.model

import com.google.gson.annotations.SerializedName

data class PersonalDevelopment(
    @SerializedName("description") val description: String
)