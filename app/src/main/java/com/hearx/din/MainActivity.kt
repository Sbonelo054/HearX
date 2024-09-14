package com.hearx.din

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.hearx.din.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayerDigits: MediaPlayer
    private lateinit var mediaPlayerNoise: MediaPlayer
    private var currentIndexNoise = 5
    private var currentIndexDigit = 0
    var numberDisplayed = 54

    private var arrayListDigits = ArrayList<Int>()
    private var arrayListNoiseLevel = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        digitInNoiseInit()
        mediaPlayerNoise = MediaPlayer.create(this, arrayListNoiseLevel[currentIndexNoise])
        mediaPlayerDigits = MediaPlayer.create(this, arrayListDigits[currentIndexDigit])
        takeDigitTriplet()
        //playSound()
    }

    private fun digitInNoiseInit(){
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

    private fun takeDigitTriplet() {
            mediaPlayerNoise.start()
            val triplet = (0..arrayListDigits.size).asSequence().shuffled().take(3).toList()

            for (index in triplet) {
                delay()
                Log.d("index","$index")
                show(index)
                delay()
            }
        }

    fun show(index:Int){
        binding.indexId.text = "$index"
        Toast.makeText(this,"index $index is playing",Toast.LENGTH_SHORT).show()
        mediaPlayerDigits = MediaPlayer.create(this, arrayListDigits[index])
        //delay()
    }

    fun delay(){
        try {
            Thread.sleep(2000)
        } catch (ie: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    fun playSound() {
        binding.indexId.text = "digit $numberDisplayed is showing"
    }
}
