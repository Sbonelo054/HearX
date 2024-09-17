package com.hearx.din.viewmodel

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearx.din.R
import com.hearx.din.model.TestRound
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class HearXViewModel(private val application: Application) : AndroidViewModel(application) {
    private var arrayListDigits: List<Int> = emptyList()
    private var arrayListNoiseLevel: List<Int> = emptyList()
    private lateinit var mediaPlayerDigits: MediaPlayer
    private lateinit var mediaPlayerNoise: MediaPlayer

    private var _currentIndexNoise = MutableLiveData(4)
    private var _tripletPlayed = MutableLiveData("")
    private var _tripletAnswered = MutableLiveData("")
    private var _numberOfRounds = MutableLiveData(1)
    private var _score = MutableLiveData(5)

    private var _testRounds: MutableLiveData<List<TestRound>> = MutableLiveData(listOf())
    val testRounds: LiveData<List<TestRound>> = _testRounds

    private fun addRound(testRound: TestRound) {
        _testRounds.value = _testRounds.value?.plus(testRound) ?: listOf(testRound)
    }

    val tripletPlayed: LiveData<String> get() = _tripletPlayed
    val tripletAnswered: LiveData<String> get() = _tripletAnswered
    val numberOfRounds: LiveData<Int> get() = _numberOfRounds
    val score: LiveData<Int> get() = _score
    private lateinit var testRound: TestRound

    fun submit(answer: String) {
        setTripletAnswered(answer)
        testRound = TestRound(_currentIndexNoise.value.toString(), _tripletPlayed.value, answer)
        addRound(testRound)
        if (_tripletPlayed.value == _tripletAnswered.value) {
            increaseNoise()
        } else {
            decreaseNoise()
        }
    }

    fun getDate() = Calendar.getInstance().time.toString().take(11)

    private fun increaseNoise() {
        if (_currentIndexNoise.value!! < 9) {
            _currentIndexNoise.value = _currentIndexNoise.value?.plus(1)
        }
        _score.value = _score.value?.plus(_currentIndexNoise.value!!)
    }

    private fun decreaseNoise() {
        if (_currentIndexNoise.value!! > 0) {
            _currentIndexNoise.value = _currentIndexNoise.value?.minus(1)
        }
        _score.value = _score.value?.minus(_currentIndexNoise.value!!)
    }

    private fun setTripletPlayed(numbersPlayed: String) {
        _tripletPlayed.value = numbersPlayed
    }

    private fun setTripletAnswered(answer: String) {
        _tripletAnswered.value = answer
    }

    fun increaseNumberOfRounds() {
        if (_numberOfRounds.value!! < 10) {
            _numberOfRounds.value = _numberOfRounds.value?.plus(1)
        }
    }

    fun randomizeDigitTriplet() {
        val triplet = arrayListDigits.indices.asSequence().shuffled().take(3).toList()
        viewModelScope.launch {
            val random = (1000 until 2000).random().toLong()
            playNoise()
            for (digit in triplet) {
                delay(random)
                playDigits(digit)
                delay(random)
            }
        }
        setTripletPlayed(triplet.joinToString(""))
    }

    fun digitInNoiseInit() {
        arrayListDigits = listOf(R.raw.digit_1, R.raw.digit_2, R.raw.digit_3, R.raw.digit_4, R.raw.digit_5, R.raw.digit_6, R.raw.digit_7, R.raw.digit_8, R.raw.digit_9)
        arrayListNoiseLevel = listOf(R.raw.noise_1, R.raw.noise_2, R.raw.noise_3, R.raw.noise_4, R.raw.noise_5, R.raw.noise_6, R.raw.noise_7, R.raw.noise_8, R.raw.noise_9, R.raw.noise_10)
    }

    private fun playDigits(digit: Int) {
        if (digit > 0) {
            mediaPlayerDigits = MediaPlayer.create(getContext(), arrayListDigits[digit - 1])
            mediaPlayerDigits.start()
        }
    }

    fun resetRounds() {
        _numberOfRounds.value = 1
    }

    private fun playNoise() {
        mediaPlayerNoise = MediaPlayer.create(getContext(), arrayListNoiseLevel[_currentIndexNoise.value!!])
        mediaPlayerNoise.start()
    }

    fun stopSound() {
        mediaPlayerNoise.stop()
        mediaPlayerDigits.stop()
    }

    private fun getContext() = application.baseContext
}