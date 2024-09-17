package com.hearx.din.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hearx.din.R
import com.hearx.din.database.TestHistoryTable
import com.hearx.din.databinding.FragmentTestBinding
import com.hearx.din.model.ResultsData
import com.hearx.din.viewmodel.HearXViewModel
import com.hearx.din.viewmodel.ScoreViewModel
import com.hearx.din.viewmodel.TestHistoryViewModel
import org.koin.android.ext.android.inject

class TestFragment : Fragment() {
    private lateinit var binding: FragmentTestBinding
    private val hearXViewModel: HearXViewModel by inject()
    private val testHistoryViewModel: TestHistoryViewModel by inject()
    private val scoreViewModel: ScoreViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)
        hearXViewModel.digitInNoiseInit()
        binding.viewModel = hearXViewModel
        binding.presenter =  this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hearXViewModel.numberOfRounds.observe(viewLifecycleOwner) {
            val round = "Round: $it"
            binding.roundNumber.text = round
        }
        hearXViewModel.newRandomizeDigitTriplet()
    }

    private fun saveHistory() {
        var score = 0
        var tripletPlayed = ""
        var tripletAnswered = ""

        hearXViewModel.numberOfRounds.observe(viewLifecycleOwner) { number ->
            if (number == 10) {
                hearXViewModel.score.observe(viewLifecycleOwner) { score = it }
                hearXViewModel.tripletPlayed.observe(viewLifecycleOwner) {
                    tripletPlayed = it
                }
                hearXViewModel.tripletAnswered.observe(viewLifecycleOwner) {
                    tripletAnswered = it
                }
                showScore(score.toString())
                testHistoryViewModel.saveTestHistory(TestHistoryTable(hearXViewModel.getDate(), score.toString(),tripletPlayed,tripletAnswered))
                hearXViewModel.resetRounds()
                hearXViewModel.testRounds.observe(viewLifecycleOwner) {list->
                    scoreViewModel.submitScore(ResultsData(score,0,list))
                }
            }
        }
    }

    private fun showScore(score: String){
        val testScore = getString(R.string.final_score_is, score)
        val meanResponse = "Mean response in ms : 500"
        val alert: Dialog?
        val builder = AlertDialog.Builder(context)
        builder.setTitle(testScore).setMessage(meanResponse).setCancelable(true).setPositiveButton(getString(R.string.ok)) { dialog: DialogInterface?, _: Int ->
            findNavController().navigateUp()
            dialog?.dismiss()
        }
        alert = builder.create()
        alert?.show()
    }

    fun submitAnswer() {
        val answer = binding.editTextNumber.text
        if (answer.isNotEmpty()) {
            hearXViewModel.newIncreaseNumberOfRounds()
            hearXViewModel.numberOfRounds.observe(viewLifecycleOwner) {
                if(it <= 10){
                    hearXViewModel.newSubmit(answer.toString())
                    hearXViewModel.newRandomizeDigitTriplet()
                } else {
                    saveHistory()
                }
            }
        } else {
            binding.editTextNumber.error = getString(R.string.please_enter_3_digits)
        }
        answer.clear()
    }

    fun navigateBack() {
        hearXViewModel.stopSound()
        findNavController().navigateUp()
    }
}