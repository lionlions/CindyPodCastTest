package com.cindy.cindypodcasttest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val TAG: String = javaClass.simpleName
    private var mCastItemViewModel: CastItemViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()


    }

    fun initViewModel(){
        mCastItemViewModel = CastItemViewModel()
    }
}