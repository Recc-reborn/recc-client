package com.recc.recc_client.layout.views

import android.animation.ObjectAnimator
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.recc.recc_client.R
import com.recc.recc_client.utils.*

class ValidatedEditTextFragment @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {
    private val linearLayout: LinearLayoutCompat
    private val etField: AppCompatEditText
    private val tvTitle: AppCompatTextView
    private val tvError: AppCompatTextView
    private val type: String?
    private val text: String?
    private val popupErrorMsg: String?
    private val textError: String?
    private var verticalOffset = 0.toFloat()
    private val horizontalOffset = 10
    private var hasBeenSet = 1

    init {
        linearLayout = inflate(context, R.layout.validated_edit_text, this) as LinearLayoutCompat
        etField = linearLayout.findViewById(R.id.et_field)
        tvTitle = linearLayout.findViewById(R.id.tv_title)
        tvError = linearLayout.findViewById(R.id.tv_error)
        etField.viewTreeObserver.addOnGlobalLayoutListener {
            verticalOffset = ((tvTitle.height) / 2 + 3).toPx(context)
            if (hasBeenSet-- > 0) {
                tvTitleAnimationIn(0)
            }
        }
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ValidatedEditText,
            0, 0).apply {
                type = getString(R.styleable.ValidatedEditText_type)
                text = getString(R.styleable.ValidatedEditText_text)
                textError = getString(R.styleable.ValidatedEditText_text_error)
                popupErrorMsg = getString(R.styleable.ValidatedEditText_popup_error_msg)
        }
        setInputType()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        // Sets regex type
        val regex = type?.let {
            Regex(context, it)
        }
        tvTitle.text = text
        tvError.text = textError

        etField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tvError.text = text
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let { charSequence ->
                    regex?.let { ex ->
                        if (charSequence.toString().matches(ex) || charSequence.toString().isEmpty()) {
                            etField.background = context.getDrawable(R.drawable.edit_text_background)
                            tvError.visibility = View.INVISIBLE
                        } else {
                            etField.background = context.getDrawable(R.drawable.edit_text_error_background)
                            tvError.visibility = View.VISIBLE
                        }
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        etField.setOnFocusChangeListener { _, notActive ->
            if (!notActive && etField.text.isNullOrEmpty()) {
                tvTitleAnimationIn()
            } else {
                tvTitleAnimationOut()
            }
        }
    }

    fun setPopupError(customError: String? = null) {
        etField.background = context.getDrawable(R.drawable.edit_text_error_background)
        customError
            ?. let { tvError.text = it }
            ?: run { tvError.text = popupErrorMsg }
        tvError.visibility = View.VISIBLE
    }

    private fun tvTitleAnimationIn(time: Long = 100) {
        ObjectAnimator.ofFloat(tvTitle, "translationX", horizontalOffset.toPx(context)).apply {
            duration = time
            start()
        }
        ObjectAnimator.ofFloat(tvTitle, "translationY", verticalOffset).apply {
            duration = time
            start()
        }
    }

    private fun tvTitleAnimationOut(time: Long = 100) {
        ObjectAnimator.ofFloat(tvTitle, "translationX", 0.toFloat()).apply {
            duration = time
            start()
        }
        ObjectAnimator.ofFloat(tvTitle, "translationY", 0.toFloat()).apply {
            duration = time
            start()
        }
    }


    private fun setInputType() {
        etField.inputType = when (type) {
            EMAIL_TYPE -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            PASSWORD_TYPE -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            USERNAME_TYPE -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            else -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
        }
    }
}