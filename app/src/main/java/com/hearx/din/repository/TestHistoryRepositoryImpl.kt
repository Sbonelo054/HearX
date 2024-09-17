package com.hearx.din.repository

import android.app.Application
import com.hearx.din.database.TestHistoryDao
import com.hearx.din.database.TestHistoryDatabase
import com.hearx.din.database.TestHistoryTable
import kotlinx.coroutines.flow.flow

class TestHistoryRepositoryImpl(application: Application) : TestHistoryRepository {
    private lateinit var dao: TestHistoryDao

    init {
        val database = TestHistoryDatabase.getInstance(application)
        if (database != null) {
            dao = database.testHistoryDao()
        }
    }

    override suspend fun saveTestHistory(testHistoryTable: TestHistoryTable) {
        dao.saveHistory(testHistoryTable)
    }

    override suspend fun getTestHistory() = flow {
        val history = dao.getHistory()
        emit(history)
    }
}