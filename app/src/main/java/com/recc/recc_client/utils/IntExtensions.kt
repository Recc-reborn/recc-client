/**
 * Extension Functions for Int values so that http codes are easier to evaluate
 */
package com.recc.recc_client.utils

import android.content.Context
import android.util.TypedValue
import java.text.DecimalFormat

fun Int.isOkCode() = this in 200..299
fun Int.isBadRequestCode() = this in 400..499
fun Int.isServerInternalErrorCode() = this in 500..599

fun Int.toPx(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics)

fun Int.millisecondsToMinutes(): String {
    var mins: Float = this.toFloat() / 60000
    val decFormat = "%05.2f"
    val format = { x: Float -> decFormat.format(x).replace('.', ':') }
    if (mins.toInt() >= 60) {
        val hours: Int = mins.toInt() / 60
        mins -= (hours * 60)
        return "${hours}:${format(mins)}"
    }
    return format(mins)
}