package com.recc.recc_client.layout.common

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.recc.recc_client.MainActivity
import com.recc.recc_client.R
import com.recc.recc_client.layout.user_msg.UserMsgScreenEvent
import com.recc.recc_client.layout.user_msg.UserMsgViewModel
import org.koin.android.ext.android.inject

/**
 * @param [T] Event type
 * @param [V] ViewModel
 * @param [B] Fragment Binding
 */
abstract class BaseFragment<T, out V: BaseEventViewModel<T>, B: ViewDataBinding>(
    private val layoutId: Int
    ) : Fragment() {
    protected abstract val viewModel: V
    private val msgViewModel: UserMsgViewModel by inject()
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

    private fun subscribeToMsgViewModel() {
        msgViewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                UserMsgScreenEvent.NoConnection -> {
                    (requireActivity() as MainActivity).enableNoConnectionView()
                }
                is UserMsgScreenEvent.PrintMessage -> {
                    Toast.makeText(requireContext(), screenEvent.msg, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    abstract fun subscribeToViewModel()

    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).disableLoadingBar()
        subscribeToViewModel()
        subscribeToMsgViewModel()
    }

    protected fun getToken(): String? {
        val sharedPref = requireActivity().getSharedPreferences(getString(R.string.preference_auth_key_file), Context.MODE_PRIVATE)
        return sharedPref.getString(getString(R.string.auth_token_key), null)
    }

    protected fun saveToken(token: String) {
        val sharedPref = requireActivity().getSharedPreferences(getString(R.string.preference_auth_key_file), Context.MODE_PRIVATE)
        with (sharedPref?.edit()) {
            this?.putString(getString(R.string.auth_token_key), token)
            this?.apply()
        }
    }
}
