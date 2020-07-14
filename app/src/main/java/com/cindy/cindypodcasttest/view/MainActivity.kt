package com.cindy.cindypodcasttest.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cindy.cindypodcasttest.api.ApiRepository
import com.cindy.cindypodcasttest.R
import com.cindy.cindypodcasttest.viewmodel.MainViewModel
import com.cindy.cindypodcasttest.viewmodel.ViewModelFactory
import com.cindy.cindypodcasttest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG: String = javaClass.simpleName
    private lateinit var mAdapter: CastAdapter
    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mViewDataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        processViewDataBinding()
        processRecyclerView()
        processViewModel()


    }

    fun processViewDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mMainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiRepository())
        ).get(MainViewModel::class.java)
        mViewDataBinding.run {
            viewModel = mMainViewModel
            lifecycleOwner = this@MainActivity
        }
    }

    fun processRecyclerView() {
        mAdapter = CastAdapter(mMainViewModel)
        mViewDataBinding.vCastList.adapter = mAdapter
    }

    fun processViewModel(){
        mMainViewModel.mCastLiveData.observe(this, Observer {
            Log.i(TAG, "mCastLiveData observer done!!")
            Log.w(TAG, "it: ${mMainViewModel.mCastLiveData.value}")
            mAdapter.notifyDataSetChanged()
        })
    }

}
