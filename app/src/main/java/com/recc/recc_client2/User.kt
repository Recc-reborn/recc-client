package com.recc.recc_client2

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)