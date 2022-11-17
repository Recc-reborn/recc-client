/**
 * Extension Functions for Int values so that http codes are easier to evaluate
 */
package com.recc.recc_client.utils

import android.content.Context
import android.util.TypedValue

fun Int.isOkCode() = this in 200..299
fun Int.isBadRequestCode() = this in 400..499
fun Int.isServerInternalErrorCode() = this in 500..599

fun Int.toPx(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics)

fun Int.secondsToMinutes(): String {
    val minutes = this / 60
    val seconds = this % 60
    val hours = minutes / 60
    return if (hours == 0) {
        "$minutes:$seconds"
    } else {
        "$hours:${ minutes % 60 }:$seconds"
    }
}