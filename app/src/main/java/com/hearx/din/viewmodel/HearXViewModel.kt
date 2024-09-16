package com.hearx.din.viewmodel

import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    var numberOfRound = 1
    var score = 5
    var testRounds: ArrayList<TestRound> = ArrayList()
    private var arrayListDigits: List<Int> = emptyList()
    private var arrayListNoiseLevel: List<Int> = emptyList()
    private lateinit var mediaPlayerDigits: MediaPlayer
    private lateinit var mediaPlayerNoise: MediaPlayer

    private var _newCurrentIndexNoise = MutableLiveData(5)
    private var _newCurrentIndexDigit = MutableLiveData(0)
    private var _newTripletPlayed = MutableLiveData("")
    private var _newTripletAnswered = MutableLiveData("")
    private var _newNumberOfRounds = MutableLiveData(1)
    private var _newScore = MutableLiveData(5)

    private var _newTestRounds: MutableLiveData<List<TestRound>> = MutableLiveData(listOf())
    val newTestRounds: LiveData<List<TestRound>> = _newTestRounds

    fun addRound(testRound: TestRound){
        _newTestRounds.value = _newTestRounds.value?.plus(testRound) ?: listOf(testRound)
    }

    //livedata
    val newCurrentIndexNoise : LiveData<Int> get() = _newCurrentIndexNoise
    val newCurrentIndexDigit : LiveData<Int> get() = _newCurrentIndexDigit
    val newTripletPlayed : LiveData<String> get() = _newTripletPlayed
    val newTripletAnswered : LiveData<String> get() = _newTripletAnswered
    val newNumberOfRounds: LiveData<Int> get() = _newNumberOfRounds
    val newScore : LiveData<Int> get() = _newScore
    private lateinit var testRound: TestRound

    fun newSubmit(answer: String) {
        setTripletAnswered(answer)
        val results = "played: ${_newTripletPlayed.value} and answered: ${_newTripletAnswered.value}"
        testRound = TestRound(_newCurrentIndexNoise.toString(), _newTripletPlayed.value, answer)
        addRound(testRound)
        if (_newTripletPlayed.value == _newTripletAnswered.value) {
            newIncreaseNoise()
        } else {
            newDecreaseNoise()
        }
        Toast.makeText(getContext(), results, Toast.LENGTH_SHORT).show()
        //keep an eye here
        if (_newNumberOfRounds.value!! <= 9) {
            newRandomizeDigitTriplet()
        } else {
            //submit the score to the api
            Log.d("Test-Rounds-Data", "$testRounds")
        }
    }

    //keep an eye here
    fun newIncreaseNoise(){
        if(_newCurrentIndexNoise.value!! < 9){
            _newCurrentIndexNoise.value = _newCurrentIndexNoise.value?.plus(1)
        }
        _newScore.value = _newScore.value?.plus(_newCurrentIndexNoise.value!!)
    }

    //keep an eye here
    fun newDecreaseNoise(){
        if(_newCurrentIndexNoise.value!! > 0){
            _newCurrentIndexNoise.value = _newCurrentIndexNoise.value?.minus(1)
        }
        _newScore.value = _newScore.value?.minus(_newCurrentIndexNoise.value!!)
    }

    fun setTripletPlayed(numbersPlayed: String){
        _newTripletPlayed.value = numbersPlayed
    }

    fun setTripletAnswered(answer: String){
        _newTripletAnswered.value = answer
    }

    fun newIncreaseNumberOfRounds(){
        _newNumberOfRounds.value = _newNumberOfRounds.value?.plus(1)
    }

    fun newRandomizeDigitTriplet() {
        val triplet = arrayListDigits.indices.asSequence().shuffled().take(3).toList()
        viewModelScope.launch {
            playNoise()
            for (digit in triplet) {
                delay(2000)
                playDigits(digit)
                delay(2000)
            }
        }
        setTripletPlayed(triplet.joinToString(""))
    }

    /*
    -------------------------------------------------------------------------------------------------------------------------------------
    */

    fun digitInNoiseInit() {
        arrayListDigits = listOf(R.raw.digit_1, R.raw.digit_2, R.raw.digit_3, R.raw.digit_4, R.raw.digit_5, R.raw.digit_6, R.raw.digit_7, R.raw.digit_8, R.raw.digit_9)
        arrayListNoiseLevel = listOf(R.raw.noise_1, R.raw.noise_2, R.raw.noise_3, R.raw.noise_4, R.raw.noise_5, R.raw.noise_6, R.raw.noise_7, R.raw.noise_8, R.raw.noise_9, R.raw.noise_10)
    }

    fun randomizeDigitTriplet() {
        val triplet = arrayListDigits.indices.asSequence().shuffled().take(3).toList()
        viewModelScope.launch {
            //randomise delay
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
        score -= currentIndexNoise
    }

    private fun increaseNoise() {
        if (currentIndexNoise < 9) {
            currentIndexNoise += 1
        }
        score += currentIndexNoise
    }

    private fun playNoise() {
        mediaPlayerNoise = MediaPlayer.create(getContext(), arrayListNoiseLevel[currentIndexNoise])
        mediaPlayerNoise.start()
    }

    fun stopSound(){
        mediaPlayerNoise.stop()
        mediaPlayerDigits.stop()
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
            randomizeDigitTriplet()
        } else {
            val completeTest = "Complete Test"
            bind.invoke().submitButton.text = completeTest
            //submit the score to the api
            Log.d("Test-Rounds-Data", "$testRounds")
            Snackbar.make(bind.invoke().root, "The test has been completed", Snackbar.LENGTH_LONG).show()
        }
        bind.invoke().editTextNumber.text.clear()
    }
}