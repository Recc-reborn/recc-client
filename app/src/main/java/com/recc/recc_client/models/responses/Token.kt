package com.recc.recc_client.models.responses

import com.google.gson.annotations.SerializedName

data class Token (
    @SerializedName("password")val password: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("device_name") val deviceName: String = "",
)