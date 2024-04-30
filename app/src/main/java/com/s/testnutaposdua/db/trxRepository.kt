package com.s.testnutaposdua.db

import com.s.testnutaposdua.model.Transaksi


class trxRepository(
        private val trxDao: trxDao
    ) {

        suspend fun insertTrx(trx: Transaksi){
            trxDao.insertTrx(trx)
        }

       fun getAllTrx(): List<Transaksi>{
            return trxDao.getAllTrx()
        }

        suspend fun updatetrx(trx: Transaksi) {
            trxDao.updateTrx(trx)
        }

        suspend fun deleteTrx(trx: Transaksi) {
            trxDao.deleteTrx(trx)
        }



    }
