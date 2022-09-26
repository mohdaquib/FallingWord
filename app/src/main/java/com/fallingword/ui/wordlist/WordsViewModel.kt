package com.fallingword.ui.wordlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fallingword.domain.Word
import com.fallingword.domain.WordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(private val wordsUseCase: WordsUseCase) : ViewModel() {
    private val _viewState = MutableStateFlow<WordsViewState>(WordsViewState.Success(emptyList()))
    val viewState: StateFlow<WordsViewState> = _viewState

    init {
        wordList()
    }

    fun wordList() {
        _viewState.value = WordsViewState.Loading
        viewModelScope.launch {
            try {
                val words = wordsUseCase.invoke()
                _viewState.value = WordsViewState.Success(words)
            } catch (e: Exception) {
                _viewState.value = WordsViewState.Error
            }
        }
    }

    fun randomWords(words: List<Word>): List<Word> {
        return words.asSequence().shuffled().take(10).toList()
    }
}

sealed class WordsViewState {
    object Loading: WordsViewState()
    object Error: WordsViewState()
    data class Success(val words: List<Word>): WordsViewState()
}