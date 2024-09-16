package com.hearx.din.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hearx.din.model.ResultsData
import com.hearx.din.repository.ScoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ScoreViewModel(private val scoreRepository: ScoreRepository): ViewModel() {
    private val _serviceResults: MutableStateFlow<String> = MutableStateFlow("")
    val serviceResult: StateFlow<String> = _serviceResults

    fun submitScore(resultsData: ResultsData) = viewModelScope.launch {
        scoreRepository.submitScore(resultsData).flowOn(Dispatchers.IO).catch {
            _serviceResults.value = it.message.toString()
        }.collect{
            _serviceResults.value = ""
        }
    }
}