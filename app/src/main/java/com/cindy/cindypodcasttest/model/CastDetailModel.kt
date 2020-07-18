package com.cindy.cindypodcasttest.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.cindy.cindypodcasttest.R
import kotlinx.android.parcel.Parcelize

data class CastDetailModel(
    var data: CastDetailData? = null
)

data class CastDetailData(
    var collection: Collection? = null
)

@Parcelize
data class Collection(
    var artistId: Int? = null,
    var artistName: String? = null,
    var artworkUrl100: String? = null,
    var artworkUrl600: String? = null,
    var collectionId: Int? = null,
    var collectionName: String? = null,
    var contentFeed: List<ContentFeed>? = null,
    var country: String? = null,
    var genreIds: String? = null,
    var genres: String? = null,
    var releaseDate: String? = null
): Parcelable {
    companion object {

        @JvmStatic
        @BindingAdapter("collectionImage")
        fun loadImage(imageView: ImageView, url: String?) {
            if(url!=null){
                Glide.with(imageView.context)
                    .applyDefaultRequestOptions(
                        RequestOptions().fitCenter().format(DecodeFormat.PREFER_RGB_565)
                    )
                    .load(url)
                    .into(imageView)
            }else{
                Glide.with(imageView.context)
                    .applyDefaultRequestOptions(
                        RequestOptions().fitCenter().format(DecodeFormat.PREFER_RGB_565)
                    )
                    .load(R.drawable.ic_baseline_broken_image_24)
                    .into(imageView)
            }
        }
    }
}

@Parcelize
data class ContentFeed(
    var contentUrl: String? = null,
    var desc: String? = null,
    var publishedDate: String? = null,
    var title: String? = null
): Parcelable