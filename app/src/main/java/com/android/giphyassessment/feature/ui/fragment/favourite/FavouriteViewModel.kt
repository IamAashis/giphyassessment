package com.android.giphyassessment.feature.ui.fragment.favourite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.giphyassessment.feature.shared.base.BaseViewModel
import com.android.giphyassessment.feature.shared.model.GiphyModel
import com.android.giphyassessment.feature.shared.repository.GiphyRepository
import com.android.giphyassessment.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Aashis on 06,September,2023
 */

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository,
    private val dispatcher: DispatcherProvider
) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _showError = MutableLiveData<String>()
    val showError: LiveData<String> = _showError

    private val _giphyList = MutableLiveData<List<GiphyModel>?>()
    val giphyList: MutableLiveData<List<GiphyModel>?> = _giphyList

    fun getGiphyData(context: Context?) {
        viewModelScope.launch(dispatcher.io) {
            _loading.postValue(true)

            try {
                val result = giphyRepository.getGiphyData(context)
                if (result != null) {
                    _giphyList.postValue(result)
                    _loading.postValue(false)
                }
            } catch (e: Exception) {

            } finally {
                _loading.postValue(false)
            }

        }
    }

    fun deleteGiphyById(context: Context, id: String) {
        viewModelScope.launch {
            giphyRepository.deleteGiphyById(context, id)
        }
    }
}