package com.android.giphyassessment.network

import com.android.giphyassessment.BuildConfig
import com.android.giphyassessment.feature.shared.model.Giphy
import com.android.giphyassessment.utils.constants.ApiConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Aashis on 05,September,2023
 */
interface ApiService {

    @GET(ApiConstants.trending)
    suspend fun getGiphy(
        @Query(ApiConstants.apikey) apiKey: String = BuildConfig.apiKey,
        @Query(ApiConstants.limit) limit: Int = 10,
        @Query(ApiConstants.offset) offset: Int?
    ): Response<Giphy>
}