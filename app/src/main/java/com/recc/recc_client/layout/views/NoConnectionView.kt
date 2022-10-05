package com.recc.recc_client.layout.views

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.recc.recc_client.MainActivity
import com.recc.recc_client.R
import com.recc.recc_client.databinding.NoConnectionViewBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class NoConnectionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val binding: NoConnectionViewBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.no_connection_view, this, true)
    private var visible = false
    private val viewModel: NoConnectionViewModel by lazy {
        (context as FragmentActivity).getViewModel()
    }
    init {
        binding.viewModel = viewModel
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun show() {
        visible = true
        visibility = View.VISIBLE
    }

    fun hide() {
        visible = false
        visibility = View.GONE
    }
}