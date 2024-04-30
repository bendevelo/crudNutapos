package com.s.testnutaposdua.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s.testnutaposdua.db.trxRepository
import com.s.testnutaposdua.mock.mocktrx
import com.s.testnutaposdua.model.Transaksi
import com.s.testnutaposdua.model.temp
import kotlinx.coroutines.launch

class trxViewModel(private val trxRepository: trxRepository) : ViewModel() {

    private val _trx = MutableLiveData<List<Transaksi>>()
    val trxs: LiveData<List<Transaksi>>
        get() = _trx

    fun getTrx() {
        viewModelScope.launch {
            val result = trxRepository.getAllTrx()
            _trx.value = result
        }


    }

    suspend fun getAlltrx(): List<Transaksi> {
        val list = trxRepository.getAllTrx()
        val listJumlah = ArrayList<temp>()
        val Newlist = ArrayList<Transaksi>()


        var y = 0;
        var amount = 0;
        var h = list.distinctBy { it.tanggal }

        for (x in h) {

            for (z in list) {

                if (x.tanggal == z.tanggal) {
                    y++
                    amount += z.jumlah
                }
            }
            listJumlah.add(temp(x.tanggal, y - 1, amount))
            amount = 0
        }

        for (x in 0..(list.size - 1)) {

            Newlist.add(Transaksi(id = list.get(x).id, tanggal = list.get(x).tanggal, jam =list.get(x).jam, kasir = list.get(x).kasir, keterangan = list.get(x).keterangan, jenis = list.get(x).jenis, sumber = list.get(x).sumber, jumlah = list.get(x).jumlah, struk = list.get(x).struk, flag = list.get(x).flag ))
            for (y in 0..listJumlah.size - 1) {

                if (x == listJumlah.get(y).index) {
                    Newlist.add(Transaksi(id = list.get(x).id, tanggal = listJumlah.get(y).tanggal, jam =list.get(x).jam, kasir = list.get(x).kasir, keterangan = list.get(x).keterangan, jenis = list.get(x).jenis, sumber = list.get(x).sumber, jumlah = listJumlah.get(y).Amount, struk = list.get(x).struk, flag = 2 ))


                }
            }

        }

        return Newlist.reversed()
    }

    fun insertTrx(trx: Transaksi) {
        viewModelScope.launch {
            trxRepository.insertTrx(trx)
        }
    }

    fun updateTrx(trx: Transaksi) {
        viewModelScope.launch {
            trxRepository.updatetrx(trx)
        }
    }

    fun deleteTrx(trx: Transaksi) {
        viewModelScope.launch {
            trxRepository.deleteTrx(trx)
        }
    }
}