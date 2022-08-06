package com.recc.recc_client.models.responses

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("message") val msg: String
)