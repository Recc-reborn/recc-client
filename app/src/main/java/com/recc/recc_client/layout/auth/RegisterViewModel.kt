package com.recc.recc_client.layout.auth

import androidx.lifecycle.viewModelScope
import com.recc.recc_client.layout.common.EventViewModel
import kotlinx.coroutines.launch

class RegisterViewModel: EventViewModel<RegisterScreenEvent>() {

    fun onBtnRegister() {
        viewModelScope.launch {
            // TODO: Creates user
            postEvent(RegisterScreenEvent.BtnRegisterPressed)
        }
    }

    fun onTvLoginInstead() {
        viewModelScope.launch {
            postEvent(RegisterScreenEvent.TvLoginInsteadPressed)
        }
    }
}