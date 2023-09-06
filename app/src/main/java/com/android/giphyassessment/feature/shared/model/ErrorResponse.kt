package com.android.giphyassessment.feature.shared.model

/**
 * Created by Aashis on 06,September,2023
 */
data class ErrorResponse(
    val errors: List<Error>? = null,
)

data class Error(
    var title: String? = null,
    var detail: String? = null,
    var source: ArrayList<String>? = null,
)