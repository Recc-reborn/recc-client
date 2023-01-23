package com.recc.recc_client.layout.views

import android.animation.ObjectAnimator
import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.recc.recc_client.R
import com.recc.recc_client.utils.RegexType
import com.recc.recc_client.utils.toPx

interface BaseEditText {
    var verticalOffset: Float
    val horizontalOffset: Int

    fun setPopupError(container: LinearLayout, errorView: TextView, customError: String? = null, popupErrorMsg: String?, context: Context) {
        container.background = context.getDrawable(R.drawable.bg_edit_text_error)
        customError
            ?. let { errorView.text = it }
            ?: run { errorView.text = popupErrorMsg }
        errorView.visibility = View.VISIBLE
    }

    fun tvTitleAnimationIn(view: View, time: Long = 100, context: Context) {
        ObjectAnimator.ofFloat(view, "translationX", horizontalOffset.toPx(context)).apply {
            duration = time
            start()
        }
        ObjectAnimator.ofFloat(view, "translationY", verticalOffset).apply {
            duration = time
            start()
        }
    }

    fun tvTitleAnimationOut(view: View, time: Long = 100) {
        ObjectAnimator.ofFloat(view, "translationX", 0.toFloat()).apply {
            duration = time
            start()
        }
        ObjectAnimator.ofFloat(view, "translationY", 0.toFloat()).apply {
            duration = time
            start()
        }
    }

    fun setInputType(fieldView: EditText, errorView: TextView, type: String) {
        fieldView.inputType = when (type) {
            RegexType.EMAIL.type -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            RegexType.PASSWORD.type -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            RegexType.USERNAME.type -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            RegexType.RAW.type -> {
                errorView.visibility = View.GONE
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            }
            else -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
        }
    }

    fun getIcon(iconType: String): Int = when (iconType) {
        IconType.SEARCH.type -> R.drawable.ic_baseline_search_24
        else -> throw IllegalArgumentException("$iconType icon type does not exist")
    }
}