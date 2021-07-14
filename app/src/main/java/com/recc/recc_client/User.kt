package com.recc.recc_client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("name")
    val name: String,
    @SerialName("role")
    val role: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)