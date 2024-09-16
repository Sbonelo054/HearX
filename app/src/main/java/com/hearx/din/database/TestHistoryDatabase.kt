package com.hearx.din.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TestHistoryTable::class], version = 1)
abstract class TestHistoryDatabase: RoomDatabase() {
    abstract fun testHistoryDao(): TestHistoryDao

    companion object {
        @Volatile
        private var instance: TestHistoryDatabase? = null

        @Synchronized
        fun getInstance(application: Application): TestHistoryDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(application,
                    TestHistoryDatabase::class.java, "test_history_database").
                fallbackToDestructiveMigration().addCallback(callback).build()
            }
            return instance
        }
        private val callback: Callback = object : Callback() {}
    }
}