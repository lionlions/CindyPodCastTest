package com.cindy.cindypodcasttest.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cindy.cindypodcasttest.BuildConfig
import com.cindy.cindypodcasttest.databinding.ItemCastDetailBinding
import com.cindy.cindypodcasttest.model.Collection
import com.cindy.cindypodcasttest.model.ContentFeed
import com.cindy.cindypodcasttest.viewmodel.CastDetailViewModel

class CastDetailAdapter(private val mViewModel: CastDetailViewModel): RecyclerView.Adapter<CastDetailAdapter.ViewHolder>() {

    private val TAG: String = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        if(BuildConfig.DEBUG)Log.v(TAG, "===== getItemCount =====")
        return mViewModel.mContentFeed.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(BuildConfig.DEBUG)Log.v(TAG, "===== onBindViewHolder =====")
        if(BuildConfig.DEBUG)Log.i(TAG, "position: $position")
        if(mViewModel.mContentFeed.value!=null
            && mViewModel.mCastDetailLiveData.value!=null){
            val collection: Collection = mViewModel.mCastDetailLiveData.value!!
            val contentFeed: ContentFeed = mViewModel.mContentFeed.value!![position]
            holder.bind(mViewModel, collection, contentFeed, position)
        }
    }

    class ViewHolder private constructor(private val mBinding: ItemCastDetailBinding): RecyclerView.ViewHolder(mBinding.root){
        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCastDetailBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
        fun bind(viewModel: CastDetailViewModel, collection: Collection, contentFeed: ContentFeed, position: Int){
            mBinding.viewModel = viewModel
            mBinding.collection = collection
            mBinding.contentFeed = contentFeed
            mBinding.position = position
            mBinding.executePendingBindings()
        }
    }

}