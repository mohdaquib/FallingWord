package com.fallingword.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fallingword.R
import com.fallingword.databinding.FragmentGameResultBinding

class GameResultFragment : Fragment(R.layout.fragment_game_result) {
    private var _binding: FragmentGameResultBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGameResultBinding.bind(view)

        val score = arguments?.getInt("score")
        binding?.scoreTextView?.text = "Hey, Your score is ${score}"
        binding?.reStartButton?.setOnClickListener {
            findNavController().navigate(R.id.action_gameResultFragment_to_wordListFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}