package com.hearx.din.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hearx.din.database.TestHistoryTable
import com.hearx.din.databinding.FragmentTestBinding
import com.hearx.din.viewmodel.HearXViewModel
import com.hearx.din.viewmodel.TestHistoryViewModel
import org.koin.android.ext.android.inject
import java.util.Calendar

class TestFragment : Fragment() {
    private lateinit var binding: FragmentTestBinding
    private val hearXViewModel: HearXViewModel by inject()
    private val testHistoryViewModel: TestHistoryViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)
        hearXViewModel.digitInNoiseInit()
        newAnswerTheTest()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hearXViewModel.newNumberOfRounds.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            val round = "Round: $it"
            binding.roundNumber.text = round
        }
        hearXViewModel.newRandomizeDigitTriplet()
    }

    private fun getBinding(): FragmentTestBinding{
        return binding
    }

    private fun answerTheTest() {
        binding.submitButton.setOnClickListener {
            val answer = binding.editTextNumber.text
            if (answer.isNotEmpty()) {
                hearXViewModel.newIncreaseNumberOfRounds()
                hearXViewModel.submit(answer.toString()){
                    getBinding()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter 3 digits", Toast.LENGTH_SHORT).show()
            }
            answer.clear()
            /*  if(hearXViewModel.numberOfRound == 10){
                  val date = Calendar.getInstance().time
                  val score = hearXViewModel.score
                  testHistoryViewModel.saveTestHistory(TestHistoryTable(date.toString(),score.toString()))
              }*/
        }
        binding.exitButton.setOnClickListener {
            hearXViewModel.stopSound()
            findNavController().navigateUp()
        }
    }
    // -----------------------------------------------------------------------------------------------------------------------------------------------------

    fun saveHistory(){
        var score = 0
        hearXViewModel.newScore.observe(viewLifecycleOwner){ score = it }
        hearXViewModel.newNumberOfRounds.observe(viewLifecycleOwner){number->
            if(number == 10){
                val date = Calendar.getInstance().time
                testHistoryViewModel.saveTestHistory(TestHistoryTable(date.toString(),score.toString()))
            }
        }
    }

    fun newAnswerTheTest(){
        binding.submitButton.setOnClickListener {
            val answer = binding.editTextNumber.text
            if(answer.isNotEmpty()){
                hearXViewModel.newIncreaseNumberOfRounds()
                hearXViewModel.newSubmit(answer.toString())
            } else {
                Toast.makeText(requireContext(), "Please enter 3 digits", Toast.LENGTH_SHORT).show()
            }
            answer.clear()
            saveHistory()
        }
        binding.exitButton.setOnClickListener {
            hearXViewModel.stopSound()
            findNavController().navigateUp()
        }
    }
}