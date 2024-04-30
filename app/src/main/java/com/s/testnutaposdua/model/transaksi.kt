package com.s.testnutaposdua.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trx")
data class Transaksi(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Long?,
    val tanggal: String,
    val jam: String,
    val kasir: String,
    val keterangan: String,
    val jenis: String,
    val sumber: String,
    val jumlah: Int,
    val struk: String,
    val flag: Int,
)
