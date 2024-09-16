package com.hearx.din.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearx.din.database.TestHistoryTable
import com.hearx.din.repository.TestHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class TestHistoryViewModel(application: Application, private val testHistoryRepository: TestHistoryRepository): AndroidViewModel(application) {
    private var _testHistory = MutableLiveData<List<TestHistoryTable>>()
    val testHistory: LiveData<List<TestHistoryTable>> get() = _testHistory

    init {
        getTestHistory()
    }

    fun saveTestHistory(testHistoryTable: TestHistoryTable){
        viewModelScope.launch(Dispatchers.IO) {
            testHistoryRepository.saveTestHistory(testHistoryTable)
        }
    }

    private fun getTestHistory() = viewModelScope.launch {
        testHistoryRepository.getTestHistory().flowOn(Dispatchers.IO).collect{
            _testHistory.value = it
        }
    }
}