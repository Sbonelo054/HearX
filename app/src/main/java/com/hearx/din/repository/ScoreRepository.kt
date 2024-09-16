package com.hearx.din.repository

import com.hearx.din.model.ResultsData

interface ScoreRepository {
    suspend fun submitScore(resultsData: ResultsData): String
}