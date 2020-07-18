package com.cindy.cindypodcasttest.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.cindy.cindypodcasttest.R
import com.cindy.cindypodcasttest.databinding.ActivityPodcastPlayerBinding
import com.cindy.cindypodcasttest.viewmodel.CastPlayerViewModel
import com.cindy.cindypodcasttest.viewmodel.ViewModelFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.TimeBar
import kotlinx.android.synthetic.main.activity_podcast_player.*
import java.util.concurrent.TimeUnit

class CastPlayerActivity : AppCompatActivity() {

    private val TAG: String = javaClass.simpleName
    private lateinit var mCastPlayerViewModel: CastPlayerViewModel
    private lateinit var mViewDataBinding: ActivityPodcastPlayerBinding
    private var mContentUrl: String? = null
    private var mTitle: String? = null
    private var mImageUrl: String? = null
    private var mCurrentPosition: Int = -1
    private var mDuration: Long = 0
    private var mExoPlayer: ExoPlayer? = null
    private var isOnPause: Boolean = false
    private lateinit var mTimerRunnalbe: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        processViewDataBinding()
        processViewModel()
        processView()

    }

    fun processViewDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_podcast_player)
        mCastPlayerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(intent = intent, context = this)
        ).get(CastPlayerViewModel::class.java)
        mViewDataBinding.run {
            viewModel = mCastPlayerViewModel
            lifecycleOwner = this@CastPlayerActivity
        }
    }

    fun processViewModel(){
        mCastPlayerViewModel.run {
            mImageUrl.observe(this@CastPlayerActivity, Observer {
                if (it != null && it!!.trim().isNotEmpty()) {
                    Glide.with(this@CastPlayerActivity)
                        .applyDefaultRequestOptions(
                            RequestOptions().fitCenter().format(DecodeFormat.PREFER_RGB_565)
                        )
                        .load(it)
                        .into(vCollectionImage)
                }
            })
            mTitle.observe(this@CastPlayerActivity, Observer {
                vTitle.text = it
            })
            mCurrentPositionLiveData.observe(this@CastPlayerActivity, Observer {
                vExoProgress.setPosition(it)
                setTime(vCurrentPosition, it)
            })
            mDuration.observe(this@CastPlayerActivity, Observer {
                vExoProgress.setDuration(it)
                setTime(vDuration, it)
            })
            isBackClick.observe(this@CastPlayerActivity, Observer {
                if(it){
                    finish()
                }
            })
        }
    }

    fun processView(){
        vExoProgress.addListener(object: TimeBar.OnScrubListener{
            override fun onScrubMove(timeBar: TimeBar, position: Long) {
            }

            override fun onScrubStart(timeBar: TimeBar, position: Long) {
            }

            override fun onScrubStop(timeBar: TimeBar, position: Long, canceled: Boolean) {
                mCastPlayerViewModel.onTimeBarScrubStop(position)
            }

        })
    }

    fun setTime(view: TextView, millisecond: Long){
        view.text = String.format("%02d : %02d",
            TimeUnit.MILLISECONDS.toMinutes(millisecond),
            TimeUnit.MILLISECONDS.toSeconds(millisecond) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisecond))
        );
    }

    override fun onPause() {
        super.onPause()
        isOnPause = true
        if (mExoPlayer != null && mExoPlayer!!.isPlaying) {
            mExoPlayer!!.playWhenReady = false
        }
        mCastPlayerViewModel.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (isOnPause && mExoPlayer != null && !mExoPlayer!!.isPlaying) {
            isOnPause = false
            mExoPlayer!!.playWhenReady = true
            mCastPlayerViewModel.onResume()
        }
    }

}