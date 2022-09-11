package com.recc.recc_client.layout.views

import android.animation.ObjectAnimator
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import com.recc.recc_client.R
import com.recc.recc_client.databinding.ValidatedEditTextBinding
import com.recc.recc_client.utils.*

class ValidatedEditTextFragment @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {
    private val binding: ValidatedEditTextBinding
    private val type: String?
    private val text: String?
    private val popupErrorMsg: String?
    private val textError: String?
    private var verticalOffset = 0.toFloat()
    private val horizontalOffset = 10
    private var hasBeenSet = false

    init {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.validated_edit_text, this, true)
        binding.etField.viewTreeObserver.addOnGlobalLayoutListener {
            verticalOffset = ((binding.tvTitle.height) / 2 + 3).toPx(context)
            if (!hasBeenSet) {
                tvTitleAnimationIn(0)
                hasBeenSet = true
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
            Regex(context, getRegexType())
        }
        binding.tvTitle.text = text
        binding.tvError.text = textError

        binding.etField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvError.text = text
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let { charSequence ->
                    regex?.let { ex ->
                        if (charSequence.toString().matches(ex) || charSequence.toString().isEmpty()) {
                            binding.etField.background = context.getDrawable(R.drawable.edit_text_background)
                            binding.tvError.visibility = View.INVISIBLE
                        } else {
                            binding.etField.background = context.getDrawable(R.drawable.edit_text_error_background)
                            binding.tvError.visibility = View.VISIBLE
                        }
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etField.setOnFocusChangeListener { _, notActive ->
            if (!notActive && binding.etField.text.isNullOrEmpty()) {
                tvTitleAnimationIn()
            } else {
                tvTitleAnimationOut()
            }
        }
    }

    fun setPopupError(customError: String? = null) {
        binding.etField.background = context.getDrawable(R.drawable.edit_text_error_background)
        customError
            ?. let { binding.tvError.text = it }
            ?: run { binding.tvError.text = popupErrorMsg }
        binding.tvError.visibility = View.VISIBLE
    }

    private fun tvTitleAnimationIn(time: Long = 100) {
        ObjectAnimator.ofFloat(binding.tvTitle, "translationX", horizontalOffset.toPx(context)).apply {
            duration = time
            start()
        }
        ObjectAnimator.ofFloat(binding.tvTitle, "translationY", verticalOffset).apply {
            duration = time
            start()
        }
    }

    private fun tvTitleAnimationOut(time: Long = 100) {
        ObjectAnimator.ofFloat(binding.tvTitle, "translationX", 0.toFloat()).apply {
            duration = time
            start()
        }
        ObjectAnimator.ofFloat(binding.tvTitle, "translationY", 0.toFloat()).apply {
            duration = time
            start()
        }
    }

    private fun getRegexType(): RegexType {
        return when (type) {
            "email" -> RegexType.EMAIL
            "password" -> RegexType.PASSWORD
            "username" -> RegexType.USERNAME
            else -> throw IllegalArgumentException("$type type argument isn't valid")
        }
    }

    private fun setInputType() {
        binding.etField.inputType = when (type) {
            "email" -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            "password" -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            "username" -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            else -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
        }
    }
}