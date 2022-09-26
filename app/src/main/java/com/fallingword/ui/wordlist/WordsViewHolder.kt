package com.fallingword.ui.wordlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fallingword.databinding.ViewpagerItemLayoutBinding
import com.fallingword.domain.Word

class WordsViewHolder(private val binding: ViewpagerItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var wordItemCallback: WordItemCallback

    fun setCallback(wordItemCallback: WordItemCallback) {
        this.wordItemCallback = wordItemCallback
    }

    fun bind(word: Word) {
        binding.englishWord.visibility = View.VISIBLE
        binding.spanishWord.visibility = View.VISIBLE
        binding.englishWord.text = word.englishText
        binding.spanishWord.text = word.spanishText
        binding.startButton.setOnClickListener {
            binding.startButton.visibility = View.GONE
            binding.correctButton.visibility = View.VISIBLE
            binding.incorrectButton.visibility = View.VISIBLE
            wordItemCallback.onStartButtonClick(binding.counter, binding.spanishWord)
        }
        binding.correctButton.setOnClickListener {
            wordItemCallback.onCorrectButtonClick(binding.spanishWord)
        }
        binding.incorrectButton.setOnClickListener {
            wordItemCallback.onIncorrectButtonClick(binding.spanishWord)
        }
    }

    companion object {
        fun create(parent: ViewGroup): WordsViewHolder {
            val wordViewPagerItemBinding = ViewpagerItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return WordsViewHolder(wordViewPagerItemBinding)
        }
    }

    interface WordItemCallback {
        fun onStartButtonClick(view: AppCompatTextView, spanishTextView: AppCompatTextView)
        fun onCorrectButtonClick(spanishTextView: AppCompatTextView)
        fun onIncorrectButtonClick(spanishTextView: AppCompatTextView)
    }
}