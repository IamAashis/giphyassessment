package com.android.giphyassessment.feature.shared.repository

import android.content.Context
import com.android.giphyassessment.database.AppDatabase
import com.android.giphyassessment.feature.shared.base.BaseRepository
import com.android.giphyassessment.feature.shared.model.Giphy
import com.android.giphyassessment.feature.shared.model.GiphyModel
import com.android.giphyassessment.network.ApiService
import com.android.giphyassessment.utils.DispatcherProvider
import com.android.giphyassessment.utils.Resources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Aashis on 06,September,2023
 */
class GiphyRepository @Inject constructor(private val api: ApiService) : BaseRepository() {

    var appDatabase: AppDatabase? = null

    suspend fun getGiphy(offset: Int): Resources<Giphy> {
        return try {
            val response = api.getGiphy(offset = offset)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resources.Success(result)
            } else {
                Resources.Error(getError(response.code(), response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Resources.Error(getError(e))
        }
    }

    fun initializeDB(context: Context?) : AppDatabase? {
        return AppDatabase.getAppDatabase(context)
    }

    fun insertData(context: Context, giphyModel: GiphyModel) {

        appDatabase = initializeDB(context)

        CoroutineScope(IO).launch {
            appDatabase!!.appDao().saveGiphyToDB(giphyModel)
        }

    }

}