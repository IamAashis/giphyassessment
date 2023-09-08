package com.android.giphyassessment.utils.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Aashis on 08,September,2023
 */
object GlobalUtils {
    fun hideKeyboard(context: Context?, view: View?) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
        view?.clearFocus()
    }
}