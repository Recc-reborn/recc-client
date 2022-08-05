package com.recc.recc_client.layout.views

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
    private val textError: String?

    init {
        linearLayout = inflate(context, R.layout.validated_edit_text, this) as LinearLayoutCompat
        etField = linearLayout.findViewById(R.id.et_field)
        tvTitle = linearLayout.findViewById(R.id.tv_title)
        tvError = linearLayout.findViewById(R.id.tv_error)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ValidatedEditText,
            0, 0).apply {
                type = getString(R.styleable.ValidatedEditText_type)
                text = getString(R.styleable.ValidatedEditText_text)
                textError = getString(R.styleable.ValidatedEditText_text_error)
        }
        setInputType()
    }

    private fun lowerView(item: View, newDp: Int) {
        val px = item.translationY
        val dp = px.toDp(context) + newDp
        item.translationY = dp.toPx(context)
    }

    private fun rightView(item: View, newDp: Int) {
        val px = item.translationX
        val dp = px.toDp(context) + newDp
        item.translationX = dp.toPx(context)
    }

    private fun resetView(view: View) {
        view.translationX = 0.toFloat()
        view.translationY = 0.toFloat()
        val param = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        val pad = context.resources.getDimension(R.dimen.form_horizontal_padding).toInt()
        param.setMargins(pad, 0, pad ,0)
        view.layoutParams
    }

    private fun setInputType() {
        etField.inputType = when (type) {
            EMAIL_TYPE -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            PASSWORD_TYPE -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            USERNAME_TYPE -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            else -> InputType.TYPE_TEXT_VARIATION_NORMAL
        }
        Alert("${etField.inputType}")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        // Sets regex type
        val regex = type?.let {
            Regex(context, it)
        }
        tvTitle.text = text
        tvError.text = textError
        lowerView(tvTitle, 39)
        rightView(tvTitle, 10)

        etField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
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
                lowerView(tvTitle, 39)
                rightView(tvTitle, 10)
            } else {
                resetView(tvTitle)
            }
        }
    }
}