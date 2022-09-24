package com.fallingword.domain

import javax.inject.Inject

class WordsUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(): List<Word> {
        return wordRepository.wordList().map(mapper)
    }

    private val mapper: (com.fallingword.data.Word) -> Word = {
        Word(it.englishText, it.spanishText)
    }
}