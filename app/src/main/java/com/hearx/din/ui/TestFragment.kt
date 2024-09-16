package com.hearx.din.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hearx.din.R
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)
        hearXViewModel.digitInNoiseInit()
        binding.viewModel = hearXViewModel
        binding.presenter =  this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hearXViewModel.newNumberOfRounds.observe(viewLifecycleOwner) {
            val round = "Round: $it"
            binding.roundNumber.text = round
        }
        hearXViewModel.newRandomizeDigitTriplet()
    }

    private fun saveHistory() {
        var score = 0
        hearXViewModel.newScore.observe(viewLifecycleOwner) { score = it }
        hearXViewModel.newNumberOfRounds.observe(viewLifecycleOwner) { number ->
            if (number == 10) {
                val date = Calendar.getInstance().time
                testHistoryViewModel.saveTestHistory(TestHistoryTable(date.toString(), score.toString()))
            }
        }
    }

    fun submitAnswer() {
        val answer = binding.editTextNumber.text
        if (answer.isNotEmpty()) {
            hearXViewModel.newIncreaseNumberOfRounds()
            hearXViewModel.newSubmit(answer.toString())
        } else {
            binding.editTextNumber.error = getString(R.string.please_enter_3_digits)
        }
        answer.clear()
        saveHistory()
    }

    fun navigateBack() {
        hearXViewModel.stopSound()
        findNavController().navigateUp()
    }
}