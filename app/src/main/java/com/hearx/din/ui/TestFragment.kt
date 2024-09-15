package com.hearx.din.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hearx.din.databinding.FragmentTestBinding
import com.hearx.din.viewmodel.HearXViewModel
import org.koin.android.ext.android.inject

class TestFragment : Fragment() {
    private lateinit var binding: FragmentTestBinding
    private val hearXViewModel: HearXViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)
        hearXViewModel.digitInNoiseInit()
        answerTheTest()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hearXViewModel.randomizeDigitTriplet{
            getBinding()
        }
    }

    private fun getBinding(): FragmentTestBinding{
        return binding
    }

    private fun answerTheTest() {
        binding.submitButton.setOnClickListener {
            val answer = binding.editTextNumber.text
            if (answer.isNotEmpty()) {
                hearXViewModel.submit(answer.toString()){
                    getBinding()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter 3 digits", Toast.LENGTH_SHORT).show()
            }
        }
        binding.exitButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}