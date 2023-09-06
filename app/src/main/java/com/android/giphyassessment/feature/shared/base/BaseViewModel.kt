package com.android.giphyassessment.feature.shared.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.giphyassessment.utils.constants.ErrorConstants
import com.android.giphyassessment.utils.customMessage.DialogMessage
import com.android.giphyassessment.utils.enums.ErrorEnum
import com.android.giphyassessment.utils.exceptions.UnAuthorizedException
import java.io.IOException
import java.net.UnknownHostException

/**
 * Created by Aashis on 06,September,2023
 */
abstract class BaseViewModel : ViewModel() {

    private val _okAction = MutableLiveData<() -> Unit?>()
    val okAction: LiveData<() -> Unit?> get() = _okAction

    private val _isLogOut = MutableLiveData<Boolean>()
    val isLogout: LiveData<Boolean> get() = _isLogOut

    private val _errorResponse = MutableLiveData<DialogMessage>()
    val errorResponse: LiveData<DialogMessage> get() = _errorResponse

    private val _errorEnumResponse = MutableLiveData<ErrorEnum?>()
    val errorEnumResponse: LiveData<ErrorEnum?> get() = _errorEnumResponse

    protected fun performActionOnException(throwable: Throwable?, okAction: () -> Unit?) {
        when (throwable) {
            is UnAuthorizedException -> {
                handleError(ErrorEnum.SessionExpired) {
                    _isLogOut.postValue(true)
                }
            }

            is UnknownHostException -> {
                handleError(ErrorEnum.NoWifi) {
                    okAction()
                }
            }

            is IOException -> {
                handleError(ErrorEnum.NoWifi) {
                    okAction()
                }
            }

            else -> {
                handleError(
                    throwable?.localizedMessage ?: ErrorConstants.defaultErrorMessage
                ) {
                    okAction()
                }
            }
        }
    }

    private fun handleError(message: String?, okAction: () -> Unit?) {
        _okAction.postValue(okAction)
        _errorResponse.postValue(DialogMessage.DynamicMessage(message))
    }

    private fun handleError(errorEnum: ErrorEnum?, okAction: () -> Unit?) {
        _okAction.postValue(okAction)
        _errorEnumResponse.postValue(errorEnum)
    }

}