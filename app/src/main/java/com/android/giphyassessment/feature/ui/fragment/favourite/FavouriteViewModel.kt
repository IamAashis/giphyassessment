package com.android.giphyassessment.feature.ui.fragment.favourite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.giphyassessment.feature.shared.base.BaseViewModel
import com.android.giphyassessment.feature.shared.model.Giphy
import com.android.giphyassessment.feature.shared.model.GiphyModel
import com.android.giphyassessment.feature.shared.repository.GiphyRepository
import com.android.giphyassessment.utils.DispatcherProvider
import com.android.giphyassessment.utils.enums.Status
import com.android.giphyassessment.utils.exceptions.onFailure
import com.android.giphyassessment.utils.exceptions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
//            giphyRepository.getGiphyData(context)

            try {
                val result = giphyRepository.getGiphyData(context)
                // Handle the success result
                if (result != null) {
                    _giphyList.postValue(result)
                    _loading.postValue(false)
                } else {
                    // Handle the case where result is null
                }
            } catch (e: Exception) {
                // Handle the failure (exception) here
            } finally {
                _loading.postValue(false)
            }

        }
    }

    private fun updateFavFromDb(giphy: Giphy?, giphyDataDb: List<GiphyModel>?): Giphy {
        val giphyData = giphy?.data

        for (i in giphyData?.indices!!) {
            for (k in giphyDataDb?.indices!!) {
                if (giphyData[i].id == giphyDataDb[k].id) {
                    giphyData[i].isFav = true
                }
            }
        }

        return giphy
    }

    fun insertData(context: Context, giphyModel: GiphyModel) {
        viewModelScope.launch {
            giphyRepository.insertData(context, giphyModel)
        }
    }

    fun deleteGiphyById(context: Context, id: String) {
        viewModelScope.launch {
            giphyRepository.deleteGiphyById(context, id)
        }
    }
}