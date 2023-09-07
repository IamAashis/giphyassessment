package com.android.giphyassessment.database.typeconvertor

import androidx.room.TypeConverter
import com.android.giphyassessment.feature.shared.model.Images
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Aashis on 07,September,2023
 */
class GiphyModelTypeConvertor {
    @TypeConverter
    fun fromImagesToString(value: Images?): String? {
        if (value == null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<Images?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromStringToImages(values: String?): Images? {
        if (values == null) {
            return null
        }
        val type = object : TypeToken<Images?>() {}.type
        return Gson().fromJson(values, type)
    }
}