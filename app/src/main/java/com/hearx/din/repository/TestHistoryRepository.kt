package com.hearx.din.repository

import androidx.lifecycle.LiveData
import com.hearx.din.database.TestHistoryTable

interface TestHistoryRepository {
    suspend fun saveTestHistory(testHistoryTable: TestHistoryTable)

    fun getTestHistory(): LiveData<List<TestHistoryTable>>
}