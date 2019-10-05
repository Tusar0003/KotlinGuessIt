package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


class GameViewModel : ViewModel() {

    companion object {
        private const val TAG = "GameViewModel"

        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

    private val timer : CountDownTimer
    private val _currentTime = MutableLiveData<Long>()
    val currentTime : LiveData<Long>
        get() = _currentTime

    val currentTimeString = Transformations.map(currentTime) {
        DateUtils.formatElapsedTime(it)
    }

    // The current word
//    var word = ""

    // internal
    private val _word = MutableLiveData<String>()
    // external
    val word : LiveData<String>
        get() = _word

    // The current score
//    var score = 0

    // internal
    private val _score = MutableLiveData<Int>()   // MutableLiveData is a live data whose value can be changed
    // external
    val score : LiveData<Int>
        get() = _score

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    init {
        Log.e(TAG, "init: GameViewModel created!")

        _eventGameFinish.value = false
        resetList()
        nextWord()
        _score.value = 0

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }

            override fun onTick(p0: Long) {
                _currentTime.value = (p0 / ONE_SECOND)
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
//        Log.e(TAG, "onCleared: GameViewModel destroyed!")
        timer.cancel()
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
//        if (wordList.isEmpty()) {
//            _eventGameFinish.value = true
//        } else {
//            _word.value = wordList.removeAt(0)
//        }

        if (wordList.isEmpty()) {
            resetList()
        }

        _word.value = wordList.removeAt(0)
    }

    /** Methods for buttons presses **/

    fun onSkip() {
//        score--
        _score.value = (score.value)?.minus(1) // Similar to subtracting with null safety
        nextWord()
    }

    fun onCorrect() {
//        score++
        _score.value = (score.value)?.plus(1) // Similar to addition with null safety
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }
}