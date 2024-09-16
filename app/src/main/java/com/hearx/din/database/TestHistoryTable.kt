package com.hearx.din.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_history")
 data class TestHistoryTable(var testDate: String, var score: String) {
 @PrimaryKey(autoGenerate = true)
 var id: Int? = null
}
