package com.fallingword.data

import com.fallingword.domain.WordRepository
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WordRepository {

    override suspend fun wordList(): List<Word> {
        return apiService.wordList()
    }
}