package com.cindy.cindypodcasttest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cindy.cindypodcasttest.api.ApiRepository

class ViewModelFactory(private val repository: ApiRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        modelClass.run {
            when{
                isAssignableFrom(CastListViewModel::class.java) -> {
                    return CastListViewModel(
                        repository
                    ) as T
                }
                isAssignableFrom(CastDetailViewModel::class.java) -> {
                    return CastDetailViewModel(
                        repository
                    ) as T
                }
                else -> {
                    throw IllegalAccessException("Unknown ViewModel Class")
                }
            }
        }
    }
}