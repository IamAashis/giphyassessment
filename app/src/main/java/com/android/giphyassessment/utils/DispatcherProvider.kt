package com.android.giphyassessment.utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Aashis on 05,September,2023
 */
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfirmed: CoroutineDispatcher
}