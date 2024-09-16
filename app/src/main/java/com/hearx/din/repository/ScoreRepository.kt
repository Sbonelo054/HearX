package com.hearx.din.repository

import com.hearx.din.model.ResultsData
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {
    suspend fun submitScore(resultsData: ResultsData): Flow<String>
}