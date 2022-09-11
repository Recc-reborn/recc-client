package com.recc.recc_client.layout.auth

import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.MainActivity
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentLoginBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.models.auth.User
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.Regex
import com.recc.recc_client.utils.RegexType
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Login Fragment that acts as an screen, it's job is limited to navigation and UI related stuff
 */
class LoginFragment : BaseFragment<LoginScreenEvent, LoginViewModel, FragmentLoginBinding>(R.layout.fragment_login) {

    override val viewModel: LoginViewModel by viewModel()

    private fun afterLoginAction(user: User) {
        Alert("stored user: $user")
        (requireActivity() as MainActivity).enableLoadingBar()
        if (user.hasSetPreferredArtists) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        } else {
            Toast.makeText(requireContext(), "It's nice to see you again!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMeData(getToken())
    }

    /**
     * Method which declares listeners for every LiveData in LoginViewModel
     */
    override fun subscribeToViewModel() {
        viewModel.emailRegex = Regex(requireContext(), RegexType.EMAIL.type)
        viewModel.passwordRegex = Regex(requireContext(), RegexType.PASSWORD.type)
        viewModel.meData.observe(viewLifecycleOwner) { user ->
            user?.let {
                afterLoginAction(it)
            }
        }


        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                is LoginScreenEvent.BtnLoginPressed -> {
                    val email = binding.vedfEmail.findViewById<EditText>(R.id.et_field).text.toString()
                    val pass = binding.vedfPassword.findViewById<EditText>(R.id.et_field).text.toString()
                    viewModel.login(email, pass)
                }
                is LoginScreenEvent.TvRegisterInsteadPressed -> {
                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }
                is LoginScreenEvent.LoginSuccessful -> {
                    saveToken(screenEvent.token)
                    viewModel.getMeData(getToken())
                }
                is LoginScreenEvent.LoginFailed -> {
                    binding.vedfEmail.setPopupError(screenEvent.errorResponse.message)
                    binding.vedfPassword.setPopupError(screenEvent.errorResponse.message)
                }
                is LoginScreenEvent.FetchMeDataFailed -> {
                    Toast.makeText(requireContext(), getString(R.string.fetch_me_data_error_msg), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}