package com.meetntrain.moviesapp.common.model

import com.squareup.moshi.Json

// expandable list example
data class Actor(
    val name: String,
    @Json(name = "profile_path") val img: String?,
    @Json(name = "known_for_department") val knownFor: String

):IMainScreenModel