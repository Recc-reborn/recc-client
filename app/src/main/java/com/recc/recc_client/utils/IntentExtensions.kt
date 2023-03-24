package com.recc.recc_client.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Intent.callSpotifyUri(context: Context, uri: String) {
    data = Uri.parse(uri)
    putExtra(
        Intent.EXTRA_REFERRER,
        Uri.parse("android-app://" + context.packageName))
    context.startActivity(this)
}