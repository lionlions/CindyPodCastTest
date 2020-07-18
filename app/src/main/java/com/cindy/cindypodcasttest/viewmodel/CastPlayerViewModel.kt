package com.cindy.cindypodcasttest.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.*
import com.cindy.cindypodcasttest.BuildConfig
import com.cindy.cindypodcasttest.R
import com.cindy.cindypodcasttest.model.Collection
import com.cindy.cindypodcasttest.model.ContentFeed
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class CastPlayerViewModel(private val mIntent: Intent?, private val mContext: Context?): ViewModel() {

    private val TAG: String = javaClass.simpleName
    private var mContentFeed: List<ContentFeed>? = null
    private var mExoPlayer: ExoPlayer? = null
    private var mContentUrl: String? = null
    private var mCurrentListPosition: Int = -1
    private lateinit var mTimerRunnalbe: Runnable
    private var mTimerHandler: Handler = Handler()
    var mTitle: MutableLiveData<String> = MutableLiveData()
    var mImageUrl: MutableLiveData<String> = MutableLiveData()
    var mDuration: MutableLiveData<Long> = MutableLiveData()
    var mCurrentPositionLiveData: MutableLiveData<Long> = MutableLiveData()
    var isReadyToPlay: MutableLiveData<Boolean> = MutableLiveData()
    var isPlayerPlaying: MutableLiveData<Boolean> = MutableLiveData()
    var isBackClick: MutableLiveData<Boolean> = MutableLiveData()

    init {
        mTimerRunnalbe = Runnable {
            mExoPlayer?.run {
                if(isPlaying){
                    mCurrentPositionLiveData.value = currentPosition
                    mTimerHandler.postDelayed(mTimerRunnalbe, 1000)
                }
            }
        }
    }

    init {
        processIntent()
        processExoAudioPlayer()
    }

    fun processIntent() {
        if (mIntent != null
            && mIntent.getParcelableExtra<Collection>("collection") != null
            && mIntent.getIntExtra("position", -1) != -1
        ) {
            val collection: Collection? = mIntent.getParcelableExtra("collection") as Collection
            collection?.run {
                val currentPosition: Int = mIntent.getIntExtra("position", -1)
                mCurrentListPosition = currentPosition
                mImageUrl.value = artworkUrl600
                mContentFeed = contentFeed
                getContentFeedInfo()
            }
        }
    }

    fun getContentFeedInfo(){
        if(mContentFeed!=null){
            mTitle.value = mContentFeed!![mCurrentListPosition].title
            mContentUrl = mContentFeed!![mCurrentListPosition].contentUrl
            if(mContext!=null && mExoPlayer!=null) {
                mExoPlayer!!.prepare(getMediaSource(mContext))
            }
        }
    }

    fun processExoAudioPlayer(){
        if(mContext==null) return
        mExoPlayer = SimpleExoPlayer.Builder(mContext).build()
        val mediaSource: MediaSource = getMediaSource(mContext)
        mExoPlayer?.run {
            prepare(mediaSource)
            playWhenReady = true
            addListener(object: Player.EventListener {
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    if(BuildConfig.DEBUG)Log.w(TAG, "playbackState: $playbackState")
                    when(playbackState){
                        Player.STATE_READY -> {
                            mDuration.value = duration
                            mTimerHandler.post(mTimerRunnalbe)
                            isReadyToPlay.value = true
                        }
                        Player.STATE_ENDED -> {
                            isReadyToPlay.value = false
                            isPlayerPlaying.value = false
                            if(mContentFeed!=null){
                                if(mCurrentListPosition == mContentFeed!!.size-1){
                                    mCurrentListPosition = 0
                                }else{
                                    mCurrentListPosition++
                                }
                                getContentFeedInfo()
                            }
                        }
                    }
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    if(BuildConfig.DEBUG)Log.w(TAG, "isPlaying: $isPlaying")
                    isPlayerPlaying.value = true
                }
            })

        }
    }

    fun getMediaSource(context: Context): MediaSource{
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context, context.getString(R.string.app_name))
        val mediaSourceFactory: ProgressiveMediaSource.Factory = ProgressiveMediaSource.Factory(dataSourceFactory)
        return mediaSourceFactory.createMediaSource(Uri.parse(mContentUrl))
    }

    fun onPause(){
        mTimerHandler.removeCallbacks(mTimerRunnalbe)
        mExoPlayer?.run { playWhenReady = false }
        isPlayerPlaying.value = false
    }

    fun onResume(){
        mTimerHandler.post(mTimerRunnalbe)
        mExoPlayer?.run { playWhenReady = true }
        isPlayerPlaying.value = true
    }

    fun onPlayClick(view: View){
        mExoPlayer?.run {
            if(isPlaying){
                onPause()
            }else{
                onResume()
            }
        }
    }

    fun onRewardClick(view: View, decreaseSec: Long){
        mExoPlayer?.run {
            val position = currentPosition
            var rewardPosition = position - decreaseSec * 1000
            rewardPosition = if(rewardPosition<0) 0 else rewardPosition
            mCurrentPositionLiveData.value = rewardPosition
            seekTo(rewardPosition)
        }
    }

    fun onForwardClick(view: View, increaseSec: Long){
        mExoPlayer?.run {
            val position = currentPosition
            var forwardPosition = position + increaseSec * 1000
            forwardPosition = if(forwardPosition>duration) duration else forwardPosition
            mCurrentPositionLiveData.value = forwardPosition
            seekTo(forwardPosition)
        }
    }

    fun onBackClick(view: View){
        isBackClick.value = true
    }

    fun onTimeBarScrubStop(position: Long) {
        mCurrentPositionLiveData.value = position
        mExoPlayer?.run { seekTo(position) }
    }

}