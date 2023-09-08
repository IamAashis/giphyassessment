package com.android.giphyassessment.feature.ui.fragment.giphy

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.giphyassessment.feature.shared.base.BaseViewModel
import com.android.giphyassessment.feature.shared.model.Giphy
import com.android.giphyassessment.feature.shared.model.GiphyModel
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
    private val _giphyList = MutableLiveData<Giphy?>()
    val giphyList: LiveData<Giphy?> = _giphyList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getGiphy(context: Context, offset: Int) {
        viewModelScope.launch(dispatcher.io) {
            _loading.postValue(true)

            val giphyDataLiveDatas = giphyRepository.getGiphyData(context)

            giphyRepository.getGiphy(offset)
                .onSuccess { responseData ->
                    _loading.postValue(false)

                    val withFavResponse = updateFavFromDb(responseData, giphyDataLiveDatas)
                    _giphyList.postValue(
                        withFavResponse
                    )

                }.onFailure { throwable ->
                    _loading.postValue(false)
                    performActionOnException(throwable) {}
                }
        }
    }

    fun searchGiphy(context: Context?, offset: Int, search: String) {
        viewModelScope.launch(dispatcher.io) {
            _loading.postValue(true)
            val giphyDataLiveDatas = giphyRepository.getGiphyData(context)
            giphyRepository.searchGiphy(search, offset)
                .onSuccess { responseData ->
                    _loading.postValue(false)
                    val withFavResponse = updateFavFromDb(responseData, giphyDataLiveDatas)
                    _giphyList.postValue(
                        withFavResponse
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