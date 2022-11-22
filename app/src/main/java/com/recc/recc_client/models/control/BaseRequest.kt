package com.recc.recc_client.models.control

import com.google.gson.annotations.SerializedName
import com.recc.recc_client.http.impl.DEFAULT_CURRENT_PAGE

data class BaseRequest<T>(
    @SerializedName("current_page") val currentPage: Int = DEFAULT_CURRENT_PAGE,
    @SerializedName("data") val data: List<T> = listOf(),
    @SerializedName("first_page_url") val firstPageUrl: String = "",
    @SerializedName("from") val from: Int = DEFAULT_CURRENT_PAGE,
    @SerializedName("last_page") val lastPage: Int = DEFAULT_CURRENT_PAGE,
    @SerializedName("last_page_url") val lastPageUrl: String = ""
)