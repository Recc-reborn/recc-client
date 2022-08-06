package com.recc.recc_client.layout.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentRegisterBinding
import com.recc.recc_client.layout.common.Event
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.emailRegex = getString(R.string.regex_email).toRegex()
        viewModel.passwordRegex = getString(R.string.regex_pass).toRegex()
        viewModel.usernameRegex = getString(R.string.regex_username).toRegex()
        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                is RegisterScreenEvent.BtnRegisterPressed -> {
                    val username = binding.vetfUsername.findViewById<EditText>(R.id.et_field).text.toString()
                    val email = binding.vetfEmail.findViewById<EditText>(R.id.et_field).text.toString()
                    val password = binding.vetfPassword.findViewById<EditText>(R.id.et_field).text.toString()
                    viewModel.register(username, email, password)
                }
                is RegisterScreenEvent.TvLoginInsteadPressed -> {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                RegisterScreenEvent.EmailAlreadyInUseCase -> {
                    binding.vetfEmail.setPopupError()
                }
            }
        })
    }
}