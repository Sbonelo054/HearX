package com.hearx.din.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResultsData(
    var score: Int = 0,
    @SerializedName("mean_response_time_ms")
    @Expose
    var meanResponseTimeMs: Int = 0,
    var rounds: List<TestRound>
)
