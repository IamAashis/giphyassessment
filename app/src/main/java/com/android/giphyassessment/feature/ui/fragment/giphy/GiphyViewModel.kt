package com.android.giphyassessment.feature.ui.fragment.giphy

import android.content.Context
import android.util.Log
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
class GiphyViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository,
    private val dispatcher: DispatcherProvider
) : BaseViewModel() {
    private val _giphyList = MutableLiveData<Giphy?>()
    val giphyList: LiveData<Giphy?> = _giphyList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

/*    private val _showError = MutableLiveData<String>()
    val showError: LiveData<String> = _showError*/

    fun getGiphy(context: Context, offset: Int) {
        viewModelScope.launch(dispatcher.io) {
            _loading.postValue(true)

            val giphyResponse = async { giphyRepository.getGiphy(offset) }
            val giphyDataLiveDatas = async { giphyRepository.getGiphyData(context) }

            val response = giphyResponse.await()
            val responseDb = giphyDataLiveDatas.await()
            val withFav = updateFavFromDb(response.data, responseDb)
            if (response.status == Status.SUCCESS) {
                _loading.postValue(false)
                _giphyList.postValue(
                    withFav
                )
            }

            /*   giphyRepository.getGiphy(offset)
                   .onSuccess { eventData ->
                       _loading.postValue(false)
                       _eventList.postValue(
                           eventData
                       )

                       val giphyDataLiveData = giphyRepository.getGiphyData(context)

                       // Observe the LiveData in the ViewModel
                       giphyDataLiveData?.observeForever { giphyData ->
                           Log.d("testDb", giphyData.toString())
   //                        _giphyLiveData.postValue(giphyData)
   //                        updateFavFromDb(eventData, giphyData)
                       }
                   }.onFailure { throwable ->
                       _loading.postValue(false)
                       performActionOnException(throwable) {}
                   }*/
        }
    }

    fun searchGiphy(offset: Int, search: String) {
        viewModelScope.launch(dispatcher.io) {
            _loading.postValue(true)
            giphyRepository.searchGiphy(search, offset)
                .onSuccess { eventData ->
                    _loading.postValue(false)
                    _giphyList.postValue(
                        eventData
                    )
                }.onFailure { throwable ->
                    _loading.postValue(false)
                    performActionOnException(throwable) {}
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