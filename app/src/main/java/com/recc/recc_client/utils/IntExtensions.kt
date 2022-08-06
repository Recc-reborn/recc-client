/**
 * Extension Functions for Int values so that http codes are easier to evaluate
 */
package com.recc.recc_client.utils

import android.content.Context
import android.util.TypedValue

fun Int.isOkCode() = this == 200
fun Int.isNotFoundCode() = this == 404
fun Int.isUnprocessableContentCode() = this == 422
fun Int.isServerInternalErrorCode() = this == 500

fun Int.toPx(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics)