package com.recc.recc_client.utils

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom

fun String.Companion.randomGenre(size: Int = 3): List<String> {
    val genres = mutableListOf<String>()
    val allowedChars = ('a'..'z') + ('A'..'Z')
    for (i in 0 until size) {
        val length = (4..8).random()
        val genre = (3 .. length).map { allowedChars.random() }.joinToString("")
        genres.add("#$genre")
    }
    return genres
}

fun String.Companion.randomString(length: Int): String {
    val allowedChars = ('0' .. '9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun String.Companion.getCodeVerifier(length: Int): String {
    val secureRandom = SecureRandom()
    val code = ByteArray(length)
    secureRandom.nextBytes(code)
    return Base64.encodeToString(code, Base64.NO_WRAP or Base64.URL_SAFE or Base64.NO_PADDING)
}

fun String.Companion.getCodeChallenge(verifier: String): String = verifier.hashSha256().encodeBase64()

fun String.hashString(type: String): String {
    val bytes = MessageDigest
        .getInstance(type)
        .digest(this.toByteArray())
    return bytes.fold("") { str, it -> str + "%02x".format(it) }
}

fun String.hashSha256(): String = this.hashString("SHA-256")

fun String.encodeBase64(): String = Base64.encodeToString(this.toByteArray(), Base64.NO_WRAP or Base64.URL_SAFE or Base64.NO_PADDING)