package com.cindy.cindypodcasttest.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.cindy.cindypodcasttest.model.Podcast
import com.cindy.cindypodcasttest.viewmodel.MainViewModel
import com.cindy.cindypodcasttest.databinding.ItemCastBinding

class CastAdapter(private val mViewModel: MainViewModel): RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    private val TAG: String = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        Log.v(TAG, "===== getItemCount =====")
        return mViewModel.mCastLiveData.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "===== onBindViewHolder =====")
        Log.i(TAG, "position: $position")
        if(mViewModel.mCastLiveData.value!=null){
            val podcast: Podcast = mViewModel.mCastLiveData.value!![position]
            holder.bind(mViewModel, podcast)
        }
    }

    class ViewHolder private constructor(private val mBinding: ItemCastBinding): RecyclerView.ViewHolder(mBinding.root){
        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCastBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
        fun bind(viewModel: MainViewModel, podcast: Podcast){
            mBinding.podcast = podcast
            mBinding.executePendingBindings()
        }
    }

}