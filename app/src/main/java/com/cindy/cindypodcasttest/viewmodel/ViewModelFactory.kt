package com.cindy.cindypodcasttest.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cindy.cindypodcasttest.api.ApiRepository

class ViewModelFactory(
    private val repository: ApiRepository? = null,
    private val intent: Intent? = null,
    private val context: Context? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        modelClass.run {
            when {
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
                isAssignableFrom(CastPlayerViewModel::class.java) -> {
                    return CastPlayerViewModel(intent, context) as T
                }
                else -> {
                    throw IllegalAccessException("Unknown ViewModel Class")
                }
            }
        }
    }
}