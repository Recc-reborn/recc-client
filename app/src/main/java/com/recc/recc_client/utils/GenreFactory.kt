package com.recc.recc_client.utils

class GenreFactory {
    companion object {
        fun generate(size: Int = 3): List<String> {
            val genres = mutableListOf<String>()
            val allowedChars = ('a'..'z') + ('A'..'Z')
            for (i in 0 until size) {
                val length = (4..8).random()
                val genre = (3 .. length).map { allowedChars.random() }.joinToString("")
                genres.add("#$genre")
            }
            return genres
        }
    }
}