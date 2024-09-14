package com.hearx.din.networking

import com.hearx.din.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    private val backingInstance: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URI).addConverterFactory(GsonConverterFactory.create()).build()
    var instance: Retrofit = backingInstance
}