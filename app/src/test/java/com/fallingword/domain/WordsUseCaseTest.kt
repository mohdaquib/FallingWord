package com.fallingword.domain

import com.fallingword.data.Word
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WordsUseCaseTest {
    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var wordsUseCase: WordsUseCase
    private val mockWordsRepository: WordRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_wordList_isNotEmpty() {
        val wordList = wordList()
        runTest {
            coEvery { mockWordsRepository.wordList() } returns wordList
            wordsUseCase = WordsUseCase(mockWordsRepository)

            val list = wordsUseCase.invoke()
            assertNotNull(list)
            assert(list.isNotEmpty())
        }
    }

    private fun wordList(): List<Word> {
        val word1 = Word("rule", "regla")
        val word2 = Word("chess", "ajedrez")
        val word3 = Word("crossword", "crucigrama")
        val word4 = Word("jigsaw", "puzzle")
        return listOf(word1, word2, word3, word4)
    }
}