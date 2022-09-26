package com.fallingword.ui.wordlist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.fallingword.domain.Word

class WordsPagerAdapter(
    private val wordItemCallback: WordsViewHolder.WordItemCallback
) : ListAdapter<Word, WordsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val wordsViewHolder = WordsViewHolder.create(parent)
        wordsViewHolder.setCallback(wordItemCallback)
        return wordsViewHolder
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.englishText == newItem.englishText
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem == newItem
            }
        }
    }
}