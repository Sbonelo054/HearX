package com.hearx.din.ui

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.google.android.material.snackbar.Snackbar
import com.hearx.din.R
import com.hearx.din.databinding.FragmentTestBinding
import com.hearx.din.model.TestRound
import com.hearx.din.viewmodel.HearXViewModel
import org.koin.android.ext.android.inject
import org.koin.compiler.metadata.declaredBindings

class TestFragment : Fragment() {
    private lateinit var binding: FragmentTestBinding
    private lateinit var mediaPlayerDigits: MediaPlayer
    private lateinit var mediaPlayerNoise: MediaPlayer
    private val hearXViewModel: HearXViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
       binding = FragmentTestBinding.inflate(inflater, container,false)
        hearXViewModel.digitInNoiseInit()
        randomizeDigitTriplet()
        answerTheTest()
        return binding.root
    }

    private fun randomizeDigitTriplet() {
        hearXViewModel.numberOfRound += 1
        binding.roundNumber.text = "Round: ${hearXViewModel.numberOfRound}"
        val triplet = (0..<hearXViewModel.arrayListDigits.size).asSequence().shuffled().take(3).toList()
        playNoise()
        for (digit in triplet) {
            //2 seconds delay
            playDigits(digit)
            //2 seconds delay
        }
        hearXViewModel.tripletPlayed = triplet.joinToString("")
    }

    private fun playNoise(){
        mediaPlayerNoise = MediaPlayer.create(requireActivity(), hearXViewModel.arrayListNoiseLevel[hearXViewModel.currentIndexNoise])
        mediaPlayerNoise.start()
    }

    private fun answerTheTest(){
        binding.submitButton.setOnClickListener {
            val answer = binding.editTextNumber.text
            if(answer.isNotEmpty()){
                hearXViewModel.tripletAnswered = answer.toString()
                val results = "played: ${hearXViewModel.tripletPlayed} and answered: ${hearXViewModel.tripletAnswered}"
                hearXViewModel.testRounds.add(TestRound(hearXViewModel.currentIndexNoise.toString(),hearXViewModel.tripletPlayed,hearXViewModel.tripletAnswered))
                if(hearXViewModel.tripletPlayed == hearXViewModel.tripletAnswered){
                    increaseNoise()
                }else{
                    decreaseNoise()
                }
                Toast.makeText(requireActivity(),results, Toast.LENGTH_SHORT).show()
                if (hearXViewModel.numberOfRound<9){
                    randomizeDigitTriplet()
                }else{
                    val completeTest = "Complete Test"
                    binding.submitButton.text = completeTest
                    //submit the score to the api
                    Snackbar.make(binding.root,"The test has been completed", Snackbar.LENGTH_LONG).show()
                }
                binding.editTextNumber.text.clear()
            } else {
                Toast.makeText(requireContext(),"Please enter 3 digits", Toast.LENGTH_SHORT).show()
            }
        }
        binding.exitButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun decreaseNoise(){
        if(hearXViewModel.currentIndexNoise>0)
        hearXViewModel.currentIndexNoise-=1
    }

    private fun increaseNoise(){
        if (hearXViewModel.currentIndexNoise<9)
        hearXViewModel.currentIndexNoise+=1
    }

    private fun playDigits(digit: Int) {
        //play digit
        mediaPlayerDigits = MediaPlayer.create(requireActivity(), hearXViewModel.arrayListDigits[digit])
        mediaPlayerDigits.start()
    }
}