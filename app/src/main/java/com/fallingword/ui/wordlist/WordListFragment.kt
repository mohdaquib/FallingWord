package com.fallingword.ui.wordlist

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.fallingword.R
import com.fallingword.databinding.FragmentWordListBinding
import com.fallingword.domain.Word
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordListFragment : Fragment(R.layout.fragment_word_list), WordsViewHolder.WordItemCallback {
    private var _binding: FragmentWordListBinding? = null
    private val binding get() = _binding

    private val wordsViewModel: WordsViewModel by viewModels()
    private lateinit var wordsPagerAdapter: WordsPagerAdapter
    private var correctCounter = 0
    private var timer: CountDownTimer? = null
    private var lastPage = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentWordListBinding.bind(view)

        lifecycleScope.launchWhenStarted {
            wordsViewModel.viewState.collect { viewState ->
                when (viewState) {
                    is WordsViewState.Loading -> {
                        binding?.loadingCircularProgressIndicator?.visibility = View.VISIBLE
                    }

                    is WordsViewState.Success -> {
                        binding?.loadingCircularProgressIndicator?.visibility = View.GONE
                        val words = viewState.words
                        setUpImageViewPager(wordsViewModel.randomWords(words))
                    }

                    is WordsViewState.Error -> {
                        binding?.loadingCircularProgressIndicator?.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setUpImageViewPager(words: List<Word>) {
        wordsPagerAdapter = WordsPagerAdapter(this)
        wordsPagerAdapter.submitList(words)
        binding?.wordsPager?.adapter = wordsPagerAdapter
        binding?.wordsPager?.isUserInputEnabled = false
        binding?.wordsPager?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == wordsPagerAdapter.itemCount - 1) {
                    lastPage = true
                }
            }
        })
    }

    override fun onStartButtonClick(view: AppCompatTextView, spanishTextView: AppCompatTextView) {
        ObjectAnimator.ofFloat(spanishTextView, "translationY", 500f).apply {
            duration = 10000
            start()
        }

        if (timer != null) {
            timer?.cancel()
        }

        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                view.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                binding?.wordsPager?.currentItem = binding?.wordsPager?.currentItem!! + 1
            }
        }
        timer?.start()
    }

    override fun onCorrectButtonClick(spanishTextView: AppCompatTextView) {
        correctCounter++
        spanishTextView.animate().translationY(0f)
        navigate()
    }

    override fun onIncorrectButtonClick(spanishTextView: AppCompatTextView) {
        spanishTextView.animate().translationY(0f)
        navigate()
    }

    private fun navigate() {
        if (lastPage) {
            val bundle = bundleOf("score" to correctCounter)
            findNavController().navigate(R.id.action_wordListFragment_to_gameResultFragment, bundle)
        } else {
            binding?.wordsPager?.currentItem = binding?.wordsPager?.currentItem!! + 1
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}