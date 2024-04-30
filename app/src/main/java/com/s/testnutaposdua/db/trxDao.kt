package com.s.testnutaposdua.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.s.testnutaposdua.model.Transaksi


@Dao
    interface trxDao {

        //create trx
        @Insert
        suspend fun insertTrx(trx: Transaksi)

        //get trx
        @Query("SELECT * FROM trx")
        fun getAllTrx(): List<Transaksi>

        //update user
        @Update
        suspend fun updateTrx(trx: Transaksi)

        //delete user
        @Delete
        suspend fun deleteTrx(trx: Transaksi)

    }
