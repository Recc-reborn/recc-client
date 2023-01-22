package com.recc.recc_client.layout.common_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.recc.recc_client.R
import com.recc.recc_client.databinding.NoConnectionViewBinding
import com.recc.recc_client.http.InterceptorViewModel
import com.recc.recc_client.layout.common.Event
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
    private val interceptorViewModel: InterceptorViewModel by lazy {
        (context as FragmentActivity).getViewModel()
    }
    private val observer = Observer<Boolean> {
        if (it) {
            hide()
        }
    }
    private val screenEventObserver = Event.EventObserver<NoConnectionScreenEvent> { screenEvent ->
        when (screenEvent) {
            NoConnectionScreenEvent.NoAccount -> {
                hide()
            }
        }
    }
    init {
        binding.viewModel = viewModel
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        interceptorViewModel.connection.observeForever(observer)
        viewModel.screenEvent.observeForever(screenEventObserver)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        interceptorViewModel.connection.removeObserver(observer)
        viewModel.screenEvent.removeObserver(screenEventObserver)
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