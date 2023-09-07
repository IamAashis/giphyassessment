package com.android.giphyassessment.feature.shared.base

import com.android.giphyassessment.feature.shared.model.ErrorResponse
import com.android.giphyassessment.feature.shared.model.GiphyModel
import com.android.giphyassessment.utils.constants.ErrorConstants
import com.android.giphyassessment.utils.constants.HttpCodeConstants
import com.android.giphyassessment.utils.exceptions.UnAuthorizedException
import com.google.gson.Gson
import java.io.IOException
import java.net.UnknownHostException

/**
 * Created by Aashis on 06,September,2023
 */
abstract class BaseRepository {

    fun getError(throwable: Throwable) =
        if (throwable is UnknownHostException || throwable is IOException) {
            throwable
        } else {
            getDefaultError()
        }

    fun getError(responseCode: Int?, error: String?): Throwable {
        return try {
            val gson = Gson()
            val root = gson.fromJson(error, ErrorResponse::class.java)
            val errorMessages = root?.errors

            if (!errorMessages.isNullOrEmpty()) {
                if (responseCode == HttpCodeConstants.unAuthorized) {
                    UnAuthorizedException(
                        errorMessages.getOrNull(0)?.detail
                            ?: ErrorConstants.defaultErrorMessage
                    )
                } else {
                    Throwable(errorMessages[0].detail)
                }

            } else {
                getDefaultError()
            }
        } catch (e: Exception) {
            getDefaultError()
        }
    }

    private fun getDefaultError() = Throwable(ErrorConstants.defaultErrorMessage)

}