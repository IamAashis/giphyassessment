package com.android.giphyassessment.feature.shared.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Aashis on 06,September,2023
 */
@Entity(tableName = "giphy")
data class GiphyModel(
    @PrimaryKey(autoGenerate = true) val idDb: Long = 0,
    var url: String? = null,
    var id: String? = null,
    var title: String? = null,
    var images: Images? = null,
    var types: String? = "giphy",
    var isFav: Boolean? = false
)
