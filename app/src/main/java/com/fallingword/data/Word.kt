package com.fallingword.data

import com.google.gson.annotations.SerializedName

data class Word(
    @SerializedName("text_eng") val englishText: String,
    @SerializedName("text_spa") val spanishText: String
)