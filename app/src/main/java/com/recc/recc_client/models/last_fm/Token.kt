package com.recc.recc_client.models.last_fm

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("token") val token: String = ""
)