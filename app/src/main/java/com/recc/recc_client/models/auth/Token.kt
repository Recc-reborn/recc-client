package com.recc.recc_client.models.auth

import com.google.gson.annotations.SerializedName

data class CreateToken(
    @SerializedName("password") val password: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("device_name") val deviceName: String = ""
)

data class Token(
    @SerializedName("token") var token: String = ""
)