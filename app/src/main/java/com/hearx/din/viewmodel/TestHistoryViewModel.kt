package com.hearx.din.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hearx.din.database.TestHistoryTable
import com.hearx.din.repository.TestHistoryRepository
import kotlinx.coroutines.launch

class TestHistoryViewModel(application: Application, private val testHistoryRepository: TestHistoryRepository): AndroidViewModel(application) {

    fun saveTestHistory(testHistoryTable: TestHistoryTable){
        viewModelScope.launch {
            testHistoryRepository.saveTestHistory(testHistoryTable)
        }
    }

    fun  getTestHistory(): LiveData<List<TestHistoryTable>>{
        return testHistoryRepository.getTestHistory()
    }
}