package com.s.testnutaposdua

import com.google.android.material.timepicker.TimeFormat
import com.s.testnutaposdua.mock.mocktrx
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val list = ArrayList<mocktrx>()
        val listJumlah = ArrayList<mocktrx>()
        val Newlist = ArrayList<mocktrx>()

        list.add(mocktrx("2020-10-11", 45))
        list.add(mocktrx("2020-10-11", 46))
        list.add(mocktrx("2020-10-11", 47))
        list.add(mocktrx("2021-10-11", 44))
        list.add(mocktrx("2021-10-11", 42))
        list.add(mocktrx("2022-10-11", 11))
        var y = 0;
        var h = list.distinctBy { it.tanggal }

        for (x in h) {

            for (z in list) {

                if (x.tanggal == z.tanggal) {
                    y++
                }

            }

            listJumlah.add(mocktrx(x.tanggal, y-1))

        }


        for (x in 0..(list.size - 1)) {

            Newlist.add(mocktrx(list.get(x).tanggal, list.get(x).amount))
            for (y in 0..listJumlah.size - 1) {

                if (x == listJumlah.get(y).amount) {
                    Newlist.add(mocktrx(listJumlah.get(y).tanggal, listJumlah.get(y).amount))

                }
            }

        }

        println(Newlist.size)


    }

//        assertEquals("4:40", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))

}