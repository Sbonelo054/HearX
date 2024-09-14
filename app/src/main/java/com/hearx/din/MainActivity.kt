package com.hearx.din

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.hearx.din.databinding.ActivityMainBinding
import com.hearx.din.viewmodel.HearXViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayerDigits: MediaPlayer
    private lateinit var mediaPlayerNoise: MediaPlayer
    private val hearXViewModel: HearXViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        answerTheTest()
        hearXViewModel.digitInNoiseInit()
        mediaPlayerDigits = MediaPlayer.create(this, hearXViewModel.arrayListDigits[hearXViewModel.currentIndexDigit])
        //if rounds <10 call takeDigitTriplet
        //should start once and iterate 9 times
        takeDigitTriplet()
    }

    private fun takeDigitTriplet() {
        hearXViewModel.numberOfRound += 1
        binding.roundNumber.text = "${hearXViewModel.numberOfRound}"
        val triplet = (0..<hearXViewModel.arrayListDigits.size).asSequence().shuffled().take(3).toList()
        hearXViewModel.triplet = triplet
        mediaPlayerNoise = MediaPlayer.create(this, hearXViewModel.arrayListNoiseLevel[hearXViewModel.currentIndexNoise])
        mediaPlayerNoise.start()
        for (index in triplet) {
            show(index)
        }
        hearXViewModel.tripletPlayed = triplet.joinToString("")
    }

    fun answerTheTest(){
        binding.submitButton.setOnClickListener {
            val answer = binding.editTextNumber.text
            hearXViewModel.tripletAnswered = answer.toString()
            val results = "played: ${hearXViewModel.tripletPlayed} and answered: ${hearXViewModel.tripletAnswered}"
            if(hearXViewModel.tripletPlayed == hearXViewModel.tripletAnswered){
                //if answer matches the digit increase noise index and play again or else decrease
                hearXViewModel.currentIndexNoise-=1
            }else{
                hearXViewModel.currentIndexNoise-=1
            }
            Toast.makeText(this,results,Toast.LENGTH_SHORT).show()
            takeDigitTriplet()
        }
    }

    fun show(index: Int) {
        //play digit
        mediaPlayerDigits = MediaPlayer.create(this, hearXViewModel.arrayListDigits[index])
    }
}
