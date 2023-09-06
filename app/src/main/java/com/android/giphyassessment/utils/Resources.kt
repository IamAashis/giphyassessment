package com.android.giphyassessment.utils

import com.android.giphyassessment.utils.enums.Status

/**
 * Created by Aashis on 06,September,2023
 */
sealed class Resources<T>(val data: T?, val status: Status, val throwable: Throwable?) {
    class Success<T>(data: T?) : Resources<T>(data, Status.SUCCESS, null)

    data class Error<T>(val error: Throwable?) : Resources<T>(null, Status.ERROR, error)
}