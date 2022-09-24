package com.fallingword.data

import retrofit2.http.GET

interface ApiService {
    @GET("words.json")
    suspend fun wordList(): List<Word>
}