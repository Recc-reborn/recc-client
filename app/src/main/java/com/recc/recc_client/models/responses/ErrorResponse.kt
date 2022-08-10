package com.recc.recc_client.models.responses

import com.google.gson.annotations.SerializedName

open class ErrorResponse {
    @SerializedName("message") val message: String = ""
}