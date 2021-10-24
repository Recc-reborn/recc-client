package com.recc.recc_client.api.schema

data class Track(
    var id: Int,
    var title: String,
    var artist: String,
    var duration: Int,
    var genre: String,
    var created_at: String,
    var updated_at: String
)

data class MutableTrack(
    var title: String,
    var artist: String,
    var duration: Int,
    var genre: String
)