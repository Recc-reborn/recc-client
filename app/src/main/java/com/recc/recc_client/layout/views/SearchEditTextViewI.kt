package com.recc.recc_client.layout.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.bumptech.glide.Glide
import com.recc.recc_client.R
import com.recc.recc_client.databinding.ViewSearchEditTextBinding
import com.recc.recc_client.utils.toPx

class SearchEditTextViewI(context: Context, attrs: AttributeSet? = null) :
    LinearLayoutCompat(context, attrs), IBaseEditText {

    override var verticalOffset: Float = 0.toFloat()
    override val horizontalOffset: Int = 10
    private val binding = ViewSearchEditTextBinding.inflate(LayoutInflater.from(context), this, true)
    private var hasBeenSet = false
    private val text: String?
    private var isEmpty = true

    init {
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
                text = getString(R.styleable.ValidatedEditText_text)
            }
    }


    override fun onFinishInflate() {
        super.onFinishInflate()

        binding.tvTitle.text = text

        binding.etField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let { charSequence ->
                    isEmpty = if (charSequence.toString().isEmpty()) {
                        setIcon(R.drawable.ic_baseline_search_24)
                        true
                    } else {
                        setIcon(R.drawable.ic_baseline_cancel_24)
                        false
                    }
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

        binding.ivIcon.apply {
            setOnClickListener {
                if (!isEmpty) {
                    binding.etField.text?.clear()
                    binding.etField.clearFocus()
                }
            }
        }
    }

    fun setIcon(icon: Int) {
        Glide.with(this)
            .load(icon)
            .fitCenter()
            .into(binding.ivIcon)
    }

    fun onSearch(callback: (search: String) -> Unit) {
        binding.etField.setOnKeyListener(object: OnKeyListener {
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                if (p2?.action == KeyEvent.ACTION_DOWN && p2.keyCode == KeyEvent.KEYCODE_ENTER) {
                    val fieldValue = binding.etField.text.toString()
                    callback(fieldValue)
                    return true
                }
                return false
            }

        })
    }

    fun onClear(callback: () -> Unit) {
        binding.ivIcon.setOnClickListener {
            if (!isEmpty) {
                binding.etField.text?.clear()
                binding.etField.clearFocus()
                tvTitleAnimationIn(binding.tvTitle, 100, context)
                callback()
            }
        }
    }
}