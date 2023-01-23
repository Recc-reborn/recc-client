package com.recc.recc_client.layout.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.recc.recc_client.http.impl.DEFAULT_CURRENT_PAGE
import kotlinx.coroutines.launch

abstract class InteractiveViewModel<T>: BaseEventViewModel<T>(){
    private val _selectedItemColor = MutableLiveData<Int>()
    val selectedItemColor: LiveData<Int> = _selectedItemColor

    private val _unselectedItemColor = MutableLiveData<Int>()
    val unselectedItemColor: LiveData<Int> = _unselectedItemColor

    private val _selectedItems = MutableLiveData<Set<Int>>()
    val selectedItems: LiveData<Set<Int>> = _selectedItems

    fun addItem(id: Int) {
        viewModelScope.launch {
            var set = mutableSetOf<Int>()
            _selectedItems.value?.let { artistsSet ->
                set = artistsSet.toMutableSet()
            }
            set.add(id)
            _selectedItems.postValue(set)
        }
    }

    fun removeItem(id: Int) {
        viewModelScope.launch {
            var set = mutableSetOf<Int>()
            _selectedItems.value?.let { artistsSet ->
                set = artistsSet.toMutableSet()
            }
            set.remove(id)
            _selectedItems.postValue(set)
        }
    }

    fun setSelectedItemColor(color: Int) {
        _selectedItemColor.postValue(color)
    }

    fun setUnselectedItemColor(color: Int) {
        _unselectedItemColor.postValue(color)
    }

    protected val _currentPage = MutableLiveData(DEFAULT_CURRENT_PAGE)
}