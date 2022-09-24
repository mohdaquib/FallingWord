package com.fallingword.data

import com.squareup.moshi.Json

data class Word(
    @field:Json(name = "text_eng") val englishText: String,
    @field:Json(name = "text_spa") val spanishText: String
)