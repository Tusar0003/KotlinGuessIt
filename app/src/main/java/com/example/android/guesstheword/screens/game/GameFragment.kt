/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    /** Constant values **/
    companion object {
        private const val TAG = "GameFragment"
    }

    private lateinit var viewModel: GameViewModel

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        Log.e(TAG, "onCreateView: Called ViewModelProviders.of!")
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel

        /**
         * It allows me to use live data to automatically update by data binding layouts
         */
        binding.setLifecycleOwner(this)

//        binding.correctButton.setOnClickListener {
//            viewModel.onCorrect()
//            updateScoreText()
//            updateWordText()
//        }

//        binding.skipButton.setOnClickListener {
//            viewModel.onSkip()
//            updateScoreText()
//            updateWordText()
//        }

//        viewModel.word.observe(this, Observer { newWord ->
//            binding.wordText.text = newWord
//        })

//        viewModel.score.observe(this, Observer {
//            binding.scoreText.text = it.toString()
//        })

        viewModel.eventGameFinish.observe(this, Observer {
            if (it) {
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        })

//        viewModel.currentTime.observe(this, Observer {
//            binding.timerText.text = DateUtils.formatElapsedTime(it)
////            binding.timerText.text = it.toString()
//        })

//        updateScoreText()
//        updateWordText()
        return binding.root

    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val currentScore = viewModel.score.value ?: 0 // ?: means that if value is not null then ok else pass through 0
        val action = GameFragmentDirections.actionGameToScore(currentScore)
        findNavController(this).navigate(action)
    }

    /** Methods for updating the UI **/

//    private fun updateWordText() {
//        binding.wordText.text = viewModel.word
//    }

//    private fun updateScoreText() {
//        binding.scoreText.text = viewModel.score.toString()
//    }
}
