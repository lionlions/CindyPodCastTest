package com.cindy.cindypodcasttest.model

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
)