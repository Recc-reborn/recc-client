package com.recc.recc_client.models.last_fm

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Attr (
    @SerializedName("page") val page: String = "",
    @SerializedName("perPage") val perPage: String = "",
    @SerializedName("totalPages") val totalPages: String = "",
    @SerializedName("total") val total: String = ""
): Serializable
