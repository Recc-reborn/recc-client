package com.recc.recc_client.layout.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentLoginBinding
import com.recc.recc_client.utils.L
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
        viewModel.loginBtnLD.observe(viewLifecycleOwner) { pressedNullable ->
            pressedNullable.let { pressed ->
                Toast.makeText(
                    requireContext(),
                    "Login btn pressed!! Value: $pressed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.registerBtnLD.observe(viewLifecycleOwner) { pressedNullable ->
            pressedNullable.let { pressed ->
                Toast.makeText(
                    requireContext(),
                    "Register btn pressed!! Value: $pressed",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }
}