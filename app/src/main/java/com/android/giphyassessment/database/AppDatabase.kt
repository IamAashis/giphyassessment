package com.android.giphyassessment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.giphyassessment.database.typeconvertor.GiphyModelTypeConvertor
import com.android.giphyassessment.feature.shared.model.GiphyModel
import com.android.giphyassessment.utils.constants.DbConstants

/**
 * Created by Aashis on 07,September,2023
 */
@Database(entities = [GiphyModel::class], version = 1)
@TypeConverters(GiphyModelTypeConvertor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getAppDatabase(context: Context?) =
            if (appDatabase != null && appDatabase?.isOpen == true) {
                appDatabase
            } else {
                if (context != null)
                    Room.databaseBuilder(context, AppDatabase::class.java, DbConstants.dbName)
                        .fallbackToDestructiveMigration()
                        .build()
                else
                    null
            }
    }
}