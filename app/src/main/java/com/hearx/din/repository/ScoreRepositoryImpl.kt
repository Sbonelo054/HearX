package com.hearx.din.repository

import com.hearx.din.model.ResultsData
import com.hearx.din.networking.API

class ScoreRepositoryImpl: ScoreRepository {
    private lateinit var api: API

    override suspend fun submitScore(resultsData: ResultsData) {
        TODO("Not yet implemented")
    }
}