package com.android.giphyassessment.utils.customviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.android.giphyassessment.utils.constants.FontConstants

/**
 * Created by Aashis on 08,September,2023
 */
class RegularTextView : AppCompatTextView {
    constructor(context: Context?) : super(context!!) {
        setFont(context!!)
    }

    constructor(context: Context?, attributeSet: AttributeSet) : super(context!!, attributeSet) {
        setFont(context!!)
    }

    constructor(context: Context?, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context!!,
        attributeSet,
        defStyleAttr
    ) {
        setFont(context!!)
    }

    private fun setFont(context: Context) {
        typeface = Typeface.createFromAsset(context.assets, FontConstants.rubikRegular)
    }
}