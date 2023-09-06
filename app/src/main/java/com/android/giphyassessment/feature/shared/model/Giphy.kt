package com.android.giphyassessment.feature.shared.model

/**
 * Created by Aashis on 06,September,2023
 */
data class Giphy(
    var data: List<GiphyModel>? = null,
    var pagination: Pagination
)
