package com.android.giphyassessment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.giphyassessment.feature.shared.model.GiphyModel
import com.android.giphyassessment.utils.constants.DbConstants

/**
 * Created by Aashis on 07,September,2023
 */
@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveGiphyToDB(giphyModel: GiphyModel)

    @Query("SELECT * FROM ${DbConstants.giphy}")
   suspend fun getAllItems(): List<GiphyModel>

    @Query("DELETE FROM ${DbConstants.giphy} WHERE id = :itemId")
    fun deleteById(itemId: String)
}