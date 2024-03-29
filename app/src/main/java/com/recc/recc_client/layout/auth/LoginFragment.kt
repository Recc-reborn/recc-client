package com.recc.recc_client.layout.auth

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.recc.recc_client.MainActivity
import com.recc.recc_client.R
import com.recc.recc_client.databinding.FragmentLoginBinding
import com.recc.recc_client.layout.common.BaseFragment
import com.recc.recc_client.layout.common.Event
import com.recc.recc_client.layout.common.MeDataViewModel
import com.recc.recc_client.models.auth.User
import com.recc.recc_client.utils.Regex
import com.recc.recc_client.utils.RegexType
import com.recc.recc_client.utils.SharedPreferences
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Login Fragment that acts as an screen, it's job is limited to navigation and UI related stuff
 */
class LoginFragment : BaseFragment<LoginScreenEvent, LoginViewModel, FragmentLoginBinding>(R.layout.fragment_login) {
    private val sharedPreferences: SharedPreferences by inject()
    override val viewModel: LoginViewModel by viewModel()
    private val meDataViewModel: MeDataViewModel by viewModel()

    private fun afterLoginAction(user: User) {
        (requireActivity() as MainActivity).enableLoadingBar()
        if (user.hasSetPreferredTracks) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        } else if (user.hasSetPreferredArtists) {
            findNavController().navigate(R.id.action_back_to_selectPreferredTracksFragment)
        } else {
            Toast.makeText(requireContext(), "It's nice to see you again!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_loginFragment_to_selectPreferredArtistsFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        sharedPreferences.getToken().let {
            viewModel.getMeData(it)
        }
    }

    /**
     * Method which declares listeners for every LiveData in LoginViewModel
     */
    override fun subscribeToViewModel() {
        viewModel.emailRegex = Regex(requireContext(), RegexType.EMAIL.type)
        viewModel.passwordRegex = Regex(requireContext(), RegexType.PASSWORD.type)

        meDataViewModel.meData.observe(viewLifecycleOwner) { nullableUser ->
            nullableUser?.let { user ->
                afterLoginAction(user)
            }
        }

        viewModel.screenEvent.observe(viewLifecycleOwner, Event.EventObserver { screenEvent ->
            when (screenEvent) {
                is LoginScreenEvent.BtnLoginPressed -> {
                    val email = binding.vedfEmail.getFieldText()
                    val pass = binding.vedfPassword.getFieldText()
                    viewModel.login(email, pass)
                }
                is LoginScreenEvent.TvRegisterInsteadPressed -> {
                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }
                is LoginScreenEvent.LoginSuccessful -> {
                    sharedPreferences.saveToken(screenEvent.token)
                    viewModel.getMeData(sharedPreferences.getToken())
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