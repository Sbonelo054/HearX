package com.hearx.din.viewmodel

import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.hearx.din.R
import com.hearx.din.databinding.FragmentTestBinding
import com.hearx.din.model.TestRound
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HearXViewModel(private val application: Application) : AndroidViewModel(application) {
    private var currentIndexNoise = 5
    private var currentIndexDigit = 0
    private var tripletPlayed = ""
    private var tripletAnswered = ""
    private var numberOfRound = 0
    private var testRounds: ArrayList<TestRound> = ArrayList()
    private var arrayListDigits: List<Int> = emptyList()
    private var arrayListNoiseLevel: List<Int> = emptyList()
    private lateinit var mediaPlayerDigits: MediaPlayer
    private lateinit var mediaPlayerNoise: MediaPlayer

    fun digitInNoiseInit() {
        arrayListDigits = listOf(R.raw.digit_1, R.raw.digit_2, R.raw.digit_3, R.raw.digit_4, R.raw.digit_5, R.raw.digit_6, R.raw.digit_7, R.raw.digit_8, R.raw.digit_9)
        arrayListNoiseLevel = listOf(R.raw.noise_1, R.raw.noise_2, R.raw.noise_3, R.raw.noise_4, R.raw.noise_5, R.raw.noise_6, R.raw.noise_7, R.raw.noise_8, R.raw.noise_9, R.raw.noise_10)
    }

    fun randomizeDigitTriplet(bind: () -> FragmentTestBinding) {
        numberOfRound += 1
        bind.invoke().roundNumber.text = "Round: $numberOfRound"
        val triplet = arrayListDigits.indices.asSequence().shuffled().take(3).toList()
        viewModelScope.launch {
            playNoise()
            for (digit in triplet) {
                delay(2000)
                playDigits(digit)
                delay(2000)
            }
        }
        tripletPlayed = triplet.joinToString("")
    }

    fun playDigits(digit: Int) {
        if (digit > 0) {
            mediaPlayerDigits = MediaPlayer.create(getContext(), arrayListDigits[digit - 1])
            mediaPlayerDigits.start()
        }
    }

    private fun decreaseNoise() {
        if (currentIndexNoise > 0) {
            currentIndexNoise -= 1
        }
    }

    private fun increaseNoise() {
        if (currentIndexNoise < 9) {
            currentIndexNoise += 1
        }
    }

    private fun playNoise() {
        mediaPlayerNoise = MediaPlayer.create(getContext(), arrayListNoiseLevel[currentIndexNoise])
        mediaPlayerNoise.start()
    }

    private fun getContext() = application.baseContext

    fun submit(answer: String, bind: () -> FragmentTestBinding) {
        tripletAnswered = answer
        val results = "played: $tripletPlayed and answered: $tripletAnswered"
        testRounds.add(TestRound(currentIndexNoise.toString(), tripletPlayed, tripletAnswered))
        if (tripletPlayed == tripletAnswered) {
            increaseNoise()
        } else {
            decreaseNoise()
        }
        Toast.makeText(getContext(), results, Toast.LENGTH_SHORT).show()
        if (numberOfRound <= 9) {
            randomizeDigitTriplet { bind() }
        } else {
            val completeTest = "Complete Test"
            bind.invoke().submitButton.text = completeTest
            //submit the score to the api
            Log.d("Test-Rounds-Data", "$testRounds")
            Snackbar.make(bind.invoke().root, "The test has been completed", Snackbar.LENGTH_LONG)
                .show()
        }
        bind.invoke().editTextNumber.text.clear()
    }
}