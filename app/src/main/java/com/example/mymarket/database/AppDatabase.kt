package com.example.mymarket.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymarket.database.dao.OfferDao
import com.example.mymarket.model.dataclass.Offer

@Database(entities = [Offer::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, context.packageName
                )
                    .build()
            }
            return instance as AppDatabase
        }
    }

    abstract fun offerDao(): OfferDao

}