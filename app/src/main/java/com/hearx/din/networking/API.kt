package com.hearx.din.networking

import com.hearx.din.model.ResultsData
import retrofit2.http.Body
import retrofit2.http.POST

interface API {

    @POST("")
    fun submitHearingResults(@Body data: ResultsData): String
}