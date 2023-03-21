package com.recc.recc_client.layout.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.Control
import com.recc.recc_client.layout.common.InteractiveItemsScreenEvent
import com.recc.recc_client.layout.common.InteractiveTracksViewModel
import com.recc.recc_client.layout.common.onFailure
import com.recc.recc_client.layout.common.onSuccess
import com.recc.recc_client.layout.recyclerview.presenters.TrackPresenter
import com.recc.recc_client.utils.Alert
import com.recc.recc_client.utils.SharedPreferences
import kotlinx.coroutines.launch

const val MIN_SELECTED_TRACKS = 1

class SelectCustomPlaylistTracksViewModel(
    private val control: Control,
    private val sharedPreferences: SharedPreferences
    ): InteractiveTracksViewModel<TrackPresenter>(control) {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    fun setTitle(title: String) {
        _title.postValue(title)
    }

    fun preferredTracksSelectedButtonPressed() {
        viewModelScope.launch viewModelScope@ {
            val token = sharedPreferences.getToken()
            if (token.isBlank() || selectedItems.value.isNullOrEmpty()) {
                Alert("no entra")
                return@viewModelScope
            }
            control.createPlaylist (token, title.value.orEmpty(), selectedItems.value!!.toMutableList())
                .onSuccess {
                    postEvent(InteractiveItemsScreenEvent.ItemsAdded)
                }.onFailure {
                    postEvent(InteractiveItemsScreenEvent.FailedAddingItems(it.message))
                }
        }
    }
}