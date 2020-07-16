package com.cindy.cindypodcasttest.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cindy.cindypodcasttest.databinding.ItemCastDetailBinding
import com.cindy.cindypodcasttest.model.ContentFeed
import com.cindy.cindypodcasttest.viewmodel.CastDetailViewModel

class CastDetailAdapter(private val mViewModel: CastDetailViewModel): RecyclerView.Adapter<CastDetailAdapter.ViewHolder>() {

    private val TAG: String = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        Log.v(TAG, "===== getItemCount =====")
        return mViewModel.mContentFeed.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "===== onBindViewHolder =====")
        Log.i(TAG, "position: $position")
        if(mViewModel.mContentFeed.value!=null){
            val contentFeed: ContentFeed = mViewModel.mContentFeed.value!![position]
            holder.bind(mViewModel, contentFeed)
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
        fun bind(viewModel: CastDetailViewModel, contentFeed: ContentFeed){
            mBinding.contentFeed = contentFeed
            mBinding.executePendingBindings()
        }
    }

}