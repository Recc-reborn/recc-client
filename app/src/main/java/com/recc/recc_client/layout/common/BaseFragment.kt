package com.recc.recc_client.layout.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel

/**
 * @param [V] ViewModel
 * @param [B] Fragment Binding
 */
abstract class BaseFragment<out V: ViewModel, B: ViewDataBinding>(
    private val layoutId: Int) : Fragment() {
    protected abstract val viewModel: V
    private var bindingNullable: B? = null
    protected val binding get() = bindingNullable!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingNullable = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.also {
            // Sets viewModel variable in layout
            binding.setVariable(BR.viewModel, viewModel)
            binding.lifecycleOwner = viewLifecycleOwner
            binding.executePendingBindings()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    abstract fun subscribeToViewModel()

    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }
}