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
        answerTheTest()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hearXViewModel.randomizeDigitTriplet()
    }
    private fun getBinding(): FragmentTestBinding{
        return binding
    }

     fun answerTheTest() {
            val answer = binding.editTextNumber.text
            if (answer.length == 3) {
                hearXViewModel.tripletAnswered = answer.toString()
                hearXViewModel.submit{
                    getBinding()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter 3 digits", Toast.LENGTH_SHORT).show()
            }
    }

    fun navigateBack(){
        findNavController().navigateUp()
    }
}