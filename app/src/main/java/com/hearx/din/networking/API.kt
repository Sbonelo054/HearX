package com.hearx.din.networking

import retrofit2.http.POST
import retrofit2.http.Path

interface API {

    @POST
    fun submitHearingResults(@Path("")v:String)
}