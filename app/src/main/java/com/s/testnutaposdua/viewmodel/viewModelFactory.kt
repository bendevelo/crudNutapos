package com.s.testnutaposdua.viewmodel

import androidx.lifecycle.ViewModel
import com.s.testnutaposdua.db.trxRepository
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val repository: trxRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(trxViewModel::class.java)) {
            return trxViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}