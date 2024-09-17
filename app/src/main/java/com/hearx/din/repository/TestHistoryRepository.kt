package com.hearx.din.repository

import com.hearx.din.database.TestHistoryTable
import kotlinx.coroutines.flow.Flow

interface TestHistoryRepository {
    suspend fun saveTestHistory(testHistoryTable: TestHistoryTable)

    suspend fun getTestHistory(): Flow<List<TestHistoryTable>>
}