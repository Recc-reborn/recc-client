package com.recc.recc_client.utils

import org.json.JSONArray

fun JSONArray.toStringList(): List<String> {
    val list = mutableListOf<String>()
    for (item in 0 until this.length()) {
        list.add(this.getString(item))
    }
    return list
}