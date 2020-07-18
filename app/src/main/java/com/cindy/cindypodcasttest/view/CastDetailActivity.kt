package com.cindy.cindypodcasttest.view

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cindy.cindypodcasttest.BuildConfig
import com.cindy.cindypodcasttest.R
import com.cindy.cindypodcasttest.api.ApiRepository
import com.cindy.cindypodcasttest.databinding.ActivityCastDetailBinding
import com.cindy.cindypodcasttest.model.Collection
import com.cindy.cindypodcasttest.viewmodel.CastDetailViewModel
import com.cindy.cindypodcasttest.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_cast_detail.*

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
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_cast_detail)
        mCastDetailViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiRepository())
        ).get(CastDetailViewModel::class.java)
        mViewDataBinding.run {
            viewModel = mCastDetailViewModel
            collection = mCastDetailViewModel.mCastDetailLiveData.value
            lifecycleOwner = this@CastDetailActivity
        }
    }

    fun processRecyclerView() {
        mAdapter = CastDetailAdapter(mCastDetailViewModel)
        vContentFeedList.adapter = mAdapter
        val layoutManager: LinearLayoutManager = vContentFeedList.layoutManager as LinearLayoutManager
        val itemDecoration: CastDetailItemDecoration = CastDetailItemDecoration(this, layoutManager.orientation, 0, 60, 0, 0)
        val dividerDrawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.cast_detail_divider)
        if(dividerDrawable!=null){
            itemDecoration.setDrawable(dividerDrawable)
        }
        vContentFeedList.addItemDecoration(itemDecoration)
    }

    fun processViewModel(){
        mCastDetailViewModel?.run {
            mContentFeed.observe(this@CastDetailActivity, Observer {
                if(BuildConfig.DEBUG)Log.i(TAG, "mCastDetailViewModel observer done!!")
                mAdapter.notifyDataSetChanged()
            })
            mCastDetailLiveData.observe(this@CastDetailActivity, Observer {
                Collection.loadImage(vCollectionImage, it.artworkUrl100)
                vCollectionName.setText(it.collectionName)
                vArtistName.setText(it.artistName)
            })
            isBackClick.observe(this@CastDetailActivity, Observer {
                if(it){
                    finish()
                }
            })
        }
    }

}