package com.hearx.din.networking

import com.hearx.din.model.ResultsData
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface API {

    @POST("")
    fun submitHearingResults(@Body data: ResultsData)
}