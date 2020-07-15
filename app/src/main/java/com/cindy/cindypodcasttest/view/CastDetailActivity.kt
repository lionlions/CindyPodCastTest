package com.cindy.cindypodcasttest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cindy.cindypodcasttest.R
import com.cindy.cindypodcasttest.api.ApiRepository
import com.cindy.cindypodcasttest.databinding.ActivityCastDetailBinding
import com.cindy.cindypodcasttest.viewmodel.CastDetailViewModel
import com.cindy.cindypodcasttest.viewmodel.CastListViewModel
import com.cindy.cindypodcasttest.viewmodel.ViewModelFactory

class CastDetailActivity : AppCompatActivity() {

    private val TAG: String = javaClass.simpleName
    private lateinit var mAdapter: CastDetailAdapter
    private lateinit var mCastDetailViewModel: CastDetailViewModel
    private lateinit var mViewDataBinding: ActivityCastDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        processViewDataBinding()
        processRecyclerView()
        processViewModel()

    }

    fun processViewDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_cast_list)
        mCastDetailViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiRepository())
        ).get(CastDetailViewModel::class.java)
        mViewDataBinding.run {
            viewModel = mCastListViewModel
            lifecycleOwner = this@CastListActivity
        }
    }

}