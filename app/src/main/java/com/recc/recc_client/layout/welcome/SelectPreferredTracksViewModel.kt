package com.recc.recc_client.layout.welcome

import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.layout.common.InteractiveItemsScreenEvent
import com.recc.recc_client.layout.common.InteractiveTracksViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.layout.views.ISelectTracksViewModel
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val MIN_SELECTED_TRACKS = 3

class SelectPreferredTracksViewModel(
    private val control: Control,
    private val sharedPreferences: SharedPreferences
    ): InteractiveTracksViewModel<TrackPresenter>(control), ISelectTracksViewModel {
    fun preferredTracksSelectedButtonPressed() {
        viewModelScope.launch viewModelScope@ {
            val token = sharedPreferences.getToken()
            if (token.isBlank() || selectedItems.value.isNullOrEmpty()) {
                Alert("no entra")
                return@viewModelScope
            }
            control.addPreferredTracks(token, selectedItems.value!!.toMutableList())
                .onSuccess {
                    postEvent(InteractiveItemsScreenEvent.ItemsAdded)
                }
                .onFailure {
                    postEvent(InteractiveItemsScreenEvent.FailedAddingItems(it.message))
                }
        }
    }
}