package com.hearx.din.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hearx.din.adapter.TestHistoryAdapter
import com.hearx.din.databinding.FragmentHistoryBinding
import com.hearx.din.viewmodel.TestHistoryViewModel
import org.koin.android.ext.android.inject

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private var adapter: TestHistoryAdapter? = null
    private val historyViewModel: TestHistoryViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showHistory()
    }

    private fun showHistory() {
        historyViewModel.testHistory.observe(viewLifecycleOwner) { response ->
            if (response.isNullOrEmpty()) {
                adapter = TestHistoryAdapter(requireContext(),response)
                binding.historyRecyclerview.setHasFixedSize(true)
                binding.historyRecyclerview.adapter = adapter
                val linearLayoutManager = LinearLayoutManager(requireActivity())
                binding.historyRecyclerview.layoutManager = linearLayoutManager
            }
        }
    }
}