package com.hearx.din.repository

import com.hearx.din.model.ResultsData
import com.hearx.din.networking.API
import com.hearx.din.networking.Client
import kotlinx.coroutines.flow.flow

class ScoreRepositoryImpl : ScoreRepository {
    private lateinit var api: API

    override suspend fun submitScore(resultsData: ResultsData) = flow {
        api = Client.instance.create(API::class.java)
        val results = api.submitHearingResults(resultsData)
        emit(results)
    }
}