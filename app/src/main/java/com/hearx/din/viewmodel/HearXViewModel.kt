package com.hearx.din.viewmodel

import androidx.lifecycle.ViewModel
import com.hearx.din.R
import com.hearx.din.model.TestRound

class HearXViewModel: ViewModel() {
    var currentIndexNoise = 5
    var currentIndexDigit = 0
    var tripletPlayed =""
    var tripletAnswered = ""
    var numberOfRound = 0
    var testRounds: ArrayList<TestRound> = ArrayList()
    var arrayListDigits = ArrayList<Int>()
    var arrayListNoiseLevel = ArrayList<Int>()

    fun digitInNoiseInit(){
        arrayListDigits.add(0, R.raw.digit_1)
        arrayListDigits.add(1, R.raw.digit_2)
        arrayListDigits.add(2, R.raw.digit_3)
        arrayListDigits.add(3, R.raw.digit_4)
        arrayListDigits.add(4, R.raw.digit_5)
        arrayListDigits.add(5, R.raw.digit_6)
        arrayListDigits.add(6, R.raw.digit_7)
        arrayListDigits.add(7, R.raw.digit_8)
        arrayListDigits.add(8, R.raw.digit_9)

        arrayListNoiseLevel.add(0, R.raw.noise_1)
        arrayListNoiseLevel.add(1, R.raw.noise_2)
        arrayListNoiseLevel.add(2, R.raw.noise_3)
        arrayListNoiseLevel.add(3, R.raw.noise_4)
        arrayListNoiseLevel.add(4, R.raw.noise_5)
        arrayListNoiseLevel.add(5, R.raw.noise_6)
        arrayListNoiseLevel.add(6, R.raw.noise_7)
        arrayListNoiseLevel.add(7, R.raw.noise_8)
        arrayListNoiseLevel.add(8, R.raw.noise_9)
        arrayListNoiseLevel.add(9, R.raw.noise_10)
    }
}