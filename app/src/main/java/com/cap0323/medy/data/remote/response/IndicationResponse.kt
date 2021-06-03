package com.cap0323.medy.data.remote.response

import com.google.gson.annotations.SerializedName

data class IndicationResponse(
    @field:SerializedName("indication")
    val indication: String? = null,
)


