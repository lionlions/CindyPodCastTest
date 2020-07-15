package com.cindy.cindypodcasttest.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions

data class CastModel(
    var data: CastData? = null
)

data class CastData(
    var podcast: List<Podcast>? = null
)

data class Podcast(
    var artistName: String? = null,
    var artworkUrl100: String? = null,
    var id: String? = null,
    var name: String? = null
) {
    companion object {

        @JvmStatic
        @BindingAdapter("castImage")
        fun loadImage(imageView: ImageView, url: String) {
            Glide.with(imageView.context)
                .applyDefaultRequestOptions(
                    RequestOptions().fitCenter().format(DecodeFormat.PREFER_RGB_565)
                )
                .load(url)
                .into(imageView)
        }
    }
}
