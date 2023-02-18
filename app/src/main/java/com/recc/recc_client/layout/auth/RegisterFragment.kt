package com.recc.recc_client.layout.auth

import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentRegisterBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.utils.Regex
import com.recc.recc_client.utils.RegexType
import com.recc.recc_client.utils.SharedPreferences
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<RegisterScreenEvent, RegisterViewModel, FragmentRegisterBinding>(R.layout.fragment_register) {
    private val sharedPreferences: SharedPreferences by inject()
    override val viewModel: RegisterViewModel by viewModel()

    override fun subscribeToViewModel() {
        viewModel.emailRegex = Regex(requireContext(), RegexType.EMAIL.type)
        viewModel.passwordRegex = Regex(requireContext(), RegexType.PASSWORD.type)
        viewModel.usernameRegex = Regex(requireContext(), RegexType.USERNAME.type)
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
                is RegisterScreenEvent.RegisterSuccessful -> {
                    viewModel.login(screenEvent.user, screenEvent.password)
                }
                is RegisterScreenEvent.RegisterFailed -> {
                    val emailErrorList = screenEvent.errorResponse.errors.email
                    val passwordErrorList = screenEvent.errorResponse.errors.password
                    if (emailErrorList.isNotEmpty()) {
                        binding.vetfEmail.setPopupError(emailErrorList.first())
                    }
                    if (passwordErrorList.isNotEmpty()) {
                        binding.vetfPassword.setPopupError(passwordErrorList.first())
                    }
                }
                is RegisterScreenEvent.LoginSuccessful -> {
                    sharedPreferences.saveToken(screenEvent.token)
                    Toast.makeText(requireContext(), "Welcome ${screenEvent.user.name}!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_selectPreferredArtistsFragment)
                }
            }
        })
    }
}