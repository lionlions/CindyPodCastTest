package com.cindy.cindypodcasttest.model
data class CastDetailModel(
    var data: CastDetailData? = null
)

data class CastDetailData(
    var collection: Collection? = null
)

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
)

data class ContentFeed(
    var contentUrl: String? = null,
    var desc: String? = null,
    var publishedDate: String? = null,
    var title: String? = null
)