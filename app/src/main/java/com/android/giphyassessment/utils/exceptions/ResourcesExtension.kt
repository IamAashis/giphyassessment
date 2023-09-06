package com.android.giphyassessment.utils.exceptions

import com.android.giphyassessment.utils.Resources

/**
 * Created by Aashis on 06,September,2023
 */

fun <T> Resources<T>.onSuccess(action: (T?) -> Unit): Resources<T> {
    if (this is Resources.Success) {
        action(data)
    }
    return this
}

fun <T> Resources<T>.onFailure(action: (Throwable?) -> Unit): Resources<T> {
    if (this is Resources.Error) {
        action(error)
    }
    return this
}