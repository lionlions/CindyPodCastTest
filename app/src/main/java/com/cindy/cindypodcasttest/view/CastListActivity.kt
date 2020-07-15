package com.cindy.cindypodcasttest.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cindy.cindypodcasttest.api.ApiRepository
import com.cindy.cindypodcasttest.R
import com.cindy.cindypodcasttest.databinding.ActivityCastListBinding
import com.cindy.cindypodcasttest.viewmodel.CastListViewModel
import com.cindy.cindypodcasttest.viewmodel.ViewModelFactory

class CastListActivity : AppCompatActivity() {

    private val TAG: String = javaClass.simpleName
    private lateinit var mAdapter: CastAdapter
    private lateinit var mCastListViewModel: CastListViewModel
    private lateinit var mViewDataBinding: ActivityCastListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        processViewDataBinding()
        processRecyclerView()
        processViewModel()

    }

    fun processViewDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_cast_list)
        mCastListViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiRepository())
        ).get(CastListViewModel::class.java)
        mViewDataBinding.run {
            viewModel = mCastListViewModel
            lifecycleOwner = this@CastListActivity
        }
    }

    fun processRecyclerView() {
        mAdapter = CastAdapter(mCastListViewModel)
        mViewDataBinding.vCastList.adapter = mAdapter
    }

    fun processViewModel(){
        mCastListViewModel.mCastLiveData.observe(this, Observer {
            Log.i(TAG, "mCastLiveData observer done!!")
            Log.w(TAG, "it: ${mCastListViewModel.mCastLiveData.value}")
            mAdapter.notifyDataSetChanged()
        })
    }

}
