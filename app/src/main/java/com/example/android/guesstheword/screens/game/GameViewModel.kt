package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GameViewModel : ViewModel() {

    // The current word
    var word = ""

    // The current score
//    var score = 0
    var score = MutableLiveData<Int>()   // MutableLiveData is a live data whose could be changed

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.e(TAG, "init: GameViewModel created!")
        resetList()
        nextWord()
        score.value = 0
    }

    override fun onCleared() {
        super.onCleared()
        Log.e(TAG, "onCleared: GameViewModel destroyed!")
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        // Select and remove a word from the list
        if (wordList.isEmpty()) {
//            gameFinished()
        } else {
            word = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
//        score--
        score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
//        score++
        score.value = (score.value)?.plus(1)
        nextWord()
    }

    companion object {
        private const val TAG = "GameViewModel"
    }
}