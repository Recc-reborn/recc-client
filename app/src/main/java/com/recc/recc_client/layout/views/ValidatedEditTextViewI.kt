package com.recc.recc_client.layout.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import com.recc.recc_client.R
import com.recc.recc_client.databinding.ViewValidatedEditTextBinding
import com.recc.recc_client.utils.*

enum class IconType(val type: String) {
    SEARCH("search")
}

class ValidatedEditTextViewI @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    override var verticalOffset: Float = 0.toFloat(),
    override val horizontalOffset: Int = 10,
) : LinearLayoutCompat(context, attrs), IBaseEditText {
    private val binding: ViewValidatedEditTextBinding
    private val type: String?
    private val text: String?
    var icon: String?
    private val popupErrorMsg: String?
    private val textError: String?
    private var hasBeenSet = false

    var callback: (() -> Unit)? = null

    init {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_validated_edit_text, this, true)
        binding.etField.viewTreeObserver.addOnGlobalLayoutListener {
            verticalOffset = ((binding.tvTitle.height) / 2 + 5).toPx(context)
            if (!hasBeenSet) {
                tvTitleAnimationIn(binding.tvTitle, 0, context)
                hasBeenSet = true
            }
        }
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ValidatedEditText,
            0,
            0)
            .apply {
                type = getString(R.styleable.ValidatedEditText_type)
                text = getString(R.styleable.ValidatedEditText_text)
                textError = getString(R.styleable.ValidatedEditText_text_error)
                icon = getString(R.styleable.ValidatedEditText_icon_img)
                popupErrorMsg = getString(R.styleable.ValidatedEditText_popup_error_msg)
        }
        type?.let {
            setInputType(binding.etField, binding.tvError, it)
        }
    }

    fun setPopupError(msg: String) {
        setPopupError(binding.llFieldContainer, binding.tvError, msg, textError, context)
    }

    fun getFieldLength(): Int = binding.etField.text?.length ?: 0

    fun getFieldEmpty(): Boolean = getFieldLength() == 0

    fun getFieldText(): String = binding.etField.text.toString()

    override fun onFinishInflate() {
        super.onFinishInflate()

        // Sets regex type
        val regex = type?.let {
            Regex(context, it)
        }
        binding.tvTitle.text = text
        binding.tvError.text = textError

        binding.etField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvError.text = textError
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let { charSequence ->
                    regex?.let { ex ->
                        if (charSequence.toString().matches(ex) || charSequence.toString().isEmpty()) {
                            binding.llFieldContainer.background = context.getDrawable(R.drawable.bg_edit_text)
                            binding.tvError.visibility = View.GONE
                        } else {
                            binding.llFieldContainer.background = context.getDrawable(R.drawable.bg_edit_text_error)
                            binding.tvError.visibility = View.VISIBLE
                        }
                    }
                    callback?.let { it() }
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etField.setOnFocusChangeListener { _, notActive ->
            if (!notActive && binding.etField.text.isNullOrEmpty()) {
                tvTitleAnimationIn(binding.tvTitle, 100, context)
            } else {
                tvTitleAnimationOut(binding.tvTitle)
            }
        }
    }
}