package com.recc.recc_client.models.last_fm

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Image(
    @SerializedName("#text") val url: String = "",
    @SerializedName("size") val size: String = ""
): Serializable