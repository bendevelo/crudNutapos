package com.s.testnutaposdua.util


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter

import java.io.ByteArrayOutputStream




object BitmapConverter {

    fun fromBitmap(bitmap: Bitmap?):String{
        val baos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
    }



    fun toBitmap(bitmap: String): Bitmap? {

        return try {
            val encodeByte = Base64.decode(bitmap, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e:Exception) {
            e.printStackTrace()
            null
        }

    }
}