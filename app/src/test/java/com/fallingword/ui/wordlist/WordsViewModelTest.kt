package com.fallingword.ui.wordlist

import com.fallingword.domain.Word
import com.fallingword.domain.WordsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WordsViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var wordsViewModel: WordsViewModel
    private val mockWordsUseCas: WordsUseCase = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_WordsViewState_Success_Event() {
        val words = wordList()
        runTest {
            coEvery { mockWordsUseCas.invoke() } returns words
            wordsViewModel = WordsViewModel(mockWordsUseCas)

            val stateFlow = wordsViewModel.viewState.first()
            assertEquals(stateFlow, WordsViewState.Success(words))
        }
    }

    @Test
    fun test_WordsViewState_Error_Event() {
        runTest {
            coEvery { mockWordsUseCas.invoke() } throws RuntimeException("")
            wordsViewModel = WordsViewModel(mockWordsUseCas)

            val stateFlow = wordsViewModel.viewState.first()
            assertEquals(stateFlow, WordsViewState.Error)
        }
    }

    @Test
    fun test_RandomWords_Data() {
        wordsViewModel = WordsViewModel(mockWordsUseCas)
        val list = wordsViewModel.randomWords(wordList())
        assertNotNull(list)
        assert(list.isNotEmpty())
    }

    private fun wordList(): List<Word> {
        val word1 = Word("rule", "regla")
        val word2 = Word("chess", "ajedrez")
        val word3 = Word("crossword", "crucigrama")
        val word4 = Word("jigsaw", "puzzle")
        return listOf(word1, word2, word3, word4)
    }
}