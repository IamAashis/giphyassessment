package com.android.giphyassessment.feature.ui.fragment.giphy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.giphyassessment.feature.shared.base.BaseViewModel
import com.android.giphyassessment.feature.shared.model.Giphy
import com.android.giphyassessment.feature.shared.repository.GiphyRepository
import com.android.giphyassessment.utils.DispatcherProvider
import com.android.giphyassessment.utils.exceptions.onFailure
import com.android.giphyassessment.utils.exceptions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Aashis on 06,September,2023
 */
@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository,
    private val dispatcher: DispatcherProvider
) : BaseViewModel() {
    private val _eventList = MutableLiveData<Giphy?>()
    val eventList: LiveData<Giphy?> = _eventList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _showError = MutableLiveData<String>()
    val showError: LiveData<String> = _showError

    fun getGiphy(offset: Int) {
        viewModelScope.launch(dispatcher.io) {
            _loading.postValue(true)

            giphyRepository.getGiphy(offset)
                .onSuccess { eventData ->
                    _loading.postValue(false)
                    _eventList.postValue(
                        eventData
                    )
                }.onFailure { throwable ->
                    _loading.postValue(false)
                    performActionOnException(throwable) {}
                }
        }
    }
}