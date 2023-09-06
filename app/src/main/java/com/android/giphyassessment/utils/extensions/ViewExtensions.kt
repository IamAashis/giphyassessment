package com.android.giphyassessment.utils.extensions

import android.view.View

/**
 * Created by Aashis on 06,September,2023
 */

fun View?.viewVisible() {
    this?.visibility = View.VISIBLE
}

fun View?.viewInVisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.viewGone() {
    this?.visibility = View.GONE
}