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

package com.example.android.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.ScoreFragmentBinding

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.score_fragment,
                container,
                false
        )

        // Get args using by navArgs property delegate
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
//        binding.scoreText.text = scoreFragmentArgs.score.toString()

        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
        /**
         * By passing viewModelFactory, I am telling ViewModelProviders to use this factory
         * to create the ScoreViewModel.
         *
         * What this means that it will use the scoreViewModel's create method to actually
         * make the ViewModel
         */
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ScoreViewModel::class.java)
        binding.scoreViewModel = viewModel

        /**
         * It allows me to use live data to automatically update by data binding layouts
         */
        binding.setLifecycleOwner(this)

//        viewModel.score.observe(this, Observer {
//            binding.scoreText.text = it.toString()
//        })

        viewModel.eventPlayAgain.observe(this, Observer {
            if (it) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        })

//        binding.playAgainButton.setOnClickListener {
//            viewModel.onPlayAgain()
//        }

        return binding.root
    }

//    private fun onPlayAgain() {
//        findNavController().navigate(ScoreFragmentDirections.actionRestart())
//    }
}
