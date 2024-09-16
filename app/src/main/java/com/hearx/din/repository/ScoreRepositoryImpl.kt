package com.hearx.din.repository

import com.hearx.din.model.ResultsData
import com.hearx.din.networking.API
import com.hearx.din.networking.Client

class ScoreRepositoryImpl: ScoreRepository {
    private lateinit var api: API

    override suspend fun submitScore(resultsData: ResultsData) {
        api = Client.instance.create(API::class.java)

    }
}