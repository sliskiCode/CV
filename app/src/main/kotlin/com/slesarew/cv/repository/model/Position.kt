package com.slesarew.cv.repository.model

import com.google.gson.annotations.SerializedName

data class Position(
    @SerializedName("title") val title: String,
    @SerializedName("dates") val dates: String,
    @SerializedName("description") val description: String,
    @SerializedName("technologies") val technologies: String
)