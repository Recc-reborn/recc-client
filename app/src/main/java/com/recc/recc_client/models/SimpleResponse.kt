package com.recc.recc_client.models.auth

import com.google.gson.annotations.SerializedName

data class SimpleResponse(
    @SerializedName("message") val message: String = ""
)