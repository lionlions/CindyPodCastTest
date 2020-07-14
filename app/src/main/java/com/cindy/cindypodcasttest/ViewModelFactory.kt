package com.cindy.cindypodcasttest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val repository: ApiRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        modelClass.run {
            when{
                isAssignableFrom(MainViewModel::class.java) -> {
                    return MainViewModel(repository) as T
                }
                else -> {
                    throw IllegalAccessException("Unknown ViewModel Class")
                }
            }
        }
    }
}