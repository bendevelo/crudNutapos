package com.s.testnutaposdua.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.s.testnutaposdua.model.Transaksi


@Database(entities = [Transaksi::class], version = 1)
    abstract class trxDatabase : RoomDatabase() {
        abstract fun trxDao(): trxDao

        companion object {
            private var INSTANCE: trxDatabase? = null

            fun getDatabaseInstance(context: Context): trxDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        trxDatabase::class.java,
                        "trx_db"
                    ).build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }
