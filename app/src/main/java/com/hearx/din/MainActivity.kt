package com.hearx.din

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.hearx.din.databinding.ActivityMainBinding
import com.hearx.din.model.TestRound
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
        randomizeDigitTriplet()
    }

    private fun randomizeDigitTriplet() {
        hearXViewModel.numberOfRound += 1
        binding.roundNumber.text = "${hearXViewModel.numberOfRound}"
        val triplet = (0..<hearXViewModel.arrayListDigits.size).asSequence().shuffled().take(3).toList()
        playNoise()
        for (digit in triplet) {
            //2 seconds delay
            playDigits(digit)
            //2 seconds delay
        }
        hearXViewModel.tripletPlayed = triplet.joinToString("")
    }

    fun playNoise(){
        mediaPlayerNoise = MediaPlayer.create(this, hearXViewModel.arrayListNoiseLevel[hearXViewModel.currentIndexNoise])
        mediaPlayerNoise.start()
    }

    fun answerTheTest(){
        binding.submitButton.setOnClickListener {
            val answer = binding.editTextNumber.text
            hearXViewModel.tripletAnswered = answer.toString()
            val results = "played: ${hearXViewModel.tripletPlayed} and answered: ${hearXViewModel.tripletAnswered}"
            hearXViewModel.testRounds.add(TestRound(hearXViewModel.currentIndexNoise.toString(),hearXViewModel.tripletPlayed,hearXViewModel.tripletAnswered))
            if(hearXViewModel.tripletPlayed == hearXViewModel.tripletAnswered){
                hearXViewModel.currentIndexNoise+=1
            }else{
                hearXViewModel.currentIndexNoise-=1
            }
            Toast.makeText(this,results,Toast.LENGTH_SHORT).show()
            if (hearXViewModel.numberOfRound<10){
                randomizeDigitTriplet()
            }else{
                //submit the score to the api
               Snackbar.make(binding.root,"The test has been completed",Snackbar.LENGTH_LONG).show()
            }
            binding.editTextNumber.text.clear()
        }
    }

    fun playDigits(digit: Int) {
        //play digit
        mediaPlayerDigits = MediaPlayer.create(this, hearXViewModel.arrayListDigits[digit])
        mediaPlayerDigits.start()
    }
}
