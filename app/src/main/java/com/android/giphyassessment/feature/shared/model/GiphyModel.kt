package com.android.giphyassessment.feature.shared.model

/**
 * Created by Aashis on 06,September,2023
 */
data class GiphyModel(
    var url: String? = null,
    var images: Images? = null,
    var types: String? = "giphy"
)
