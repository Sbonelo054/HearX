package com.hearx.din.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TestHistoryDao {
    @Insert
    suspend fun saveHistory(testHistoryTable: TestHistoryTable)

    @Query("SELECT * FROM test_history")
    fun getHistory(): LiveData<List<TestHistoryTable>>
}