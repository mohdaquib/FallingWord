package com.fallingword.domain

import com.fallingword.data.Word

interface WordRepository {
    suspend fun wordList(): List<Word>
}