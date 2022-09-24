package com.fallingword.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fallingword.domain.WordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(private val wordsUseCase: WordsUseCase) : ViewModel() {

    fun wordList() {
        viewModelScope.launch {
            val words = wordsUseCase.invoke()
            Log.d("Words", words.toString())
        }
    }
}