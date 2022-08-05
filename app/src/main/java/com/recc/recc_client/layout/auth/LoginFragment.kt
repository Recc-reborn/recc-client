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
import com.recc.recc_client.databinding.FragmentLoginBinding
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.utils.Regex
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Login Fragment that acts as an screen, it's job is limited to navigation and UI related stuff
 */
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }

    /**
     * Method which declares listeners for every LiveData in LoginViewModel
     */
    private fun subscribeToViewModel() {
        viewModel.emailRegex = Regex(requireContext(), "email")
        viewModel.passwordRegex = Regex(requireContext(), "password")

        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                LoginScreenEvent.BtnLoginPressed -> {
                    val email = binding.vedfEmail.findViewById<EditText>(R.id.et_field).text.toString()
                    val pass = binding.vedfPassword.findViewById<EditText>(R.id.et_field).text.toString()
                    viewModel.login(email, pass)
                }
                LoginScreenEvent.TvRegisterInsteadPressed -> {
                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }
            }
        })
    }
}