package com.hearx.din.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hearx.din.R
import com.hearx.din.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.presenter = this
        return binding.root
    }

    fun navigateToTest() {
        findNavController().navigate(R.id.action_homeFragment_to_testFragment)
    }

    fun navigateToHistory() {
        findNavController().navigate(R.id.action_homeFragment_to_testHistoryFragment)
    }
}
